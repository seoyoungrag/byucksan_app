package com.sds.acube.app.login.security.seed;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class SeedBean
{
    public final static String ROUNDKEY = "roundkey"; // 세션에 담아 두는 키이름

    // Byte을 String으로 변화하는 배열
    public static char[] convertChar = {
            '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    };

    // 라운드키를 복호화하기 위한 라운드키
    public static int[] pdwRoundKey = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0,
    };

    // 라운드키를 복호화하기 위한 라운드키
    private static int[] pdwBasicRoundKey = {
            1559650149, 201465340, 1285763400, 910680113, -85072379, -87161836, -1553785472,
            -1396808815, -1091168792, -147394669, -1875497525, 2001726595, -482177040, -1001045466,
            -351698974, 775711409, 1771962331, 755543676, -1309034586, 712735490, 1560486927,
            -2081174469, -805735717, 1016148399, 191372860, 391980102, 78406790, -91249324,
            -433858263, 130851589, 1036975240, -1130611916,
    };


    private static int charToInt(char src) throws Exception
    {
        // + = 43 / - = 45 /
        // 0 = 48 / 9 = 57
        // A = 65 / Z = 90
        // a = 97 / z = 122
        int result = -1;
        switch (src)
        {
        case '+':
            result = 0;
            break;
        case '-':
            result = 1;
            break;
        default:
            if ((src >= 48) && (src <= 57)) result = src - 46;
            if ((src >= 65) && (src <= 90)) result = src - 53; // 12
            if ((src >= 97) && (src <= 122)) result = src - 59; // 38
        }
        return result;
    }


    public static byte[] asciiTobyte(String src, int length) throws Exception
    {
        byte[] resultOrg = new byte[src.length() / 4 * 3];

        int pindex = 0;
        for (int i = 0; i < src.length(); i = i + 4)
        {
            int iValue1 = charToInt(src.charAt(i));
            int iValue2 = charToInt(src.charAt(i + 1));
            int iValue3 = charToInt(src.charAt(i + 2));
            int iValue4 = charToInt(src.charAt(i + 3));

            resultOrg[pindex++] = (byte) ((iValue1 << 2) + (iValue2 >> 4));
            resultOrg[pindex++] = (byte) ((iValue2 << 4) + (iValue3 >> 2));
            resultOrg[pindex++] = (byte) ((iValue3 << 6) + (iValue4));
        }

        return resultOrg;
    }


    public static void setRoundKey(HttpServletRequest request) throws Exception
    {
        HttpSession session = request.getSession();

        String roundkey = request.getParameter("roundkey");
        if (roundkey == null) throw new Exception("RoundKey Null");

        byte[] byteData = asciiTobyte(roundkey, 1);
        byte pbCipher[] = new byte[16];
        byte pbPlain[] = new byte[16];

        for (int i = 0; i < byteData.length / 16; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                pbCipher[j] = byteData[i * 16 + j];
            }
            Seedx.SeedDecrypt(pbCipher, pdwBasicRoundKey, pbPlain);

            for (int j = 0; j < 16; j++)
            {
                byteData[i * 16 + j] = pbPlain[j];
            }
        }

        int byteDataLen = byteData.length;
        for (byteDataLen = byteData.length; byteDataLen > 0; byteDataLen--)
        {
            if (byteData[byteDataLen - 1] != 0x00) break;
        }

        String aaaa = new String(byteData, 0, byteDataLen);

        // 콤마 단위로 분리하여 32개의 Integer 배열 담는다.
        int[] ia_roundkey = new int[32];
        StringTokenizer st = new StringTokenizer(aaaa, ",");
        int i = 0;
        while (st.hasMoreElements())
        {
            String tmpaa = st.nextToken();
            ia_roundkey[i++] = Integer.parseInt(tmpaa);
        }

        session.setAttribute(ROUNDKEY, ia_roundkey);
    }


    public static String doDecode(HttpServletRequest request, String data) throws Exception
    {
        // 세션에서 라운드키 가져오기
        HttpSession session = request.getSession();
        int[] roundkey = (int[]) session.getAttribute(ROUNDKEY);
        return doDecode(roundkey, data);
    }


    public static String doEncrypt(HttpServletRequest request, String data) throws Exception
    {
        // 세션에서 라운드키 가져오기
        HttpSession session = request.getSession();
        int[] roundkey = (int[]) session.getAttribute(ROUNDKEY);
        return doEncrypt(roundkey, data);
    }


    public static String doDecode(String data) throws Exception
    {
        return doDecode(pdwBasicRoundKey, data);
    }


    public static String doDecode(int[] roundkey, String data) throws Exception
    {
        byte[] byteData = asciiTobyte(data, 1);
        byte pbCipher[] = new byte[16];
        byte pbPlain[] = new byte[16];

        for (int i = 0; i < byteData.length / 16; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                pbCipher[j] = byteData[i * 16 + j];
            }
            Seedx.SeedDecrypt(pbCipher, roundkey, pbPlain);

            for (int j = 0; j < 16; j++)
            {
                byteData[i * 16 + j] = pbPlain[j];
            }
        }

        int byteDataLen = byteData.length;
        for (byteDataLen = byteData.length; byteDataLen > 0; byteDataLen--)
        {
            if (byteData[byteDataLen - 1] != 0x00) break;
        }

        String aaaa = new String(byteData, 0, byteDataLen);

        return aaaa;
    }


    public static String doDecode(String data, String keyFile) throws Exception
    {
        if (pdwRoundKey[0] == 0) doReadRoundKey(keyFile);
        return doDecode(pdwRoundKey, data);
    }


    private static void doReadRoundKey(String keyFile) throws Exception
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(keyFile)));
        String line = doDecode(in.readLine());
        in.close();

        StringTokenizer st = new StringTokenizer(line, ",");
        int index = 0;
        while (st.hasMoreTokens())
        {
            String tmpOneRkey = st.nextToken();
            pdwRoundKey[index++] = Integer.parseInt(tmpOneRkey);
        }
    }


    public static String doEncrypt(String data) throws Exception
    {
        return doEncrypt(pdwBasicRoundKey, data);
    }


    public static String doEncrypt(String data, String keyFile) throws Exception
    {
        if (pdwRoundKey[0] == 0) doReadRoundKey(keyFile);
        return doEncrypt(pdwRoundKey, data);
    }


    public static String doEncrypt(int[] roundkey, String data)
    {
        if (data == null)
        {
            return null;
        }

        for (int k = 0; k < 32; k++)
        {
            if (roundkey[k] != 0) break;
            if (k == 31)
            {
                return null;
            }
        }

        StringBuffer buffer = new StringBuffer();
        byte[] pbCipher = new byte[16];
        byte[] pbData2 = new byte[16];
        byte[] pbData1 = new byte[18];

        byte[] szData = data.getBytes();
        int pdDataLen = szData.length;

        if (pdDataLen == 0)
        {
            return null;
        }

        int sindex = 0;
        for (int i = 0; i <= (pdDataLen / 16); i++)
        {
            for (int j = 0; j < 16; j++)
            {
                if (i * 16 + j < pdDataLen)
                {
                    pbData2[j] = szData[i * 16 + j];
                }
                else
                {
                    pbData2[j] = '\0';
                }
            }
            // Encryption Algorithm
            Seedx.SeedEncrypt(pbData2, roundkey, pbCipher);

            for (int j = 0; j < 16; j++)
            {
                pbData1[sindex + j] = pbCipher[j];
            }

            for (int j = 0; j < 5 + ((sindex + 1) / 3); j++)
            {
                buffer.append(convertChar[pbData1[j * 3] >> 2 & 0x3F]);
                buffer.append(convertChar[(pbData1[j * 3] << 4 & 0x30)
                        + (pbData1[j * 3 + 1] >> 4 & 0x0F)]);
                buffer.append(convertChar[(pbData1[j * 3 + 1] << 2 & 0x3C)
                        + (pbData1[j * 3 + 2] >> 6 & 0x03)]);
                buffer.append(convertChar[pbData1[j * 3 + 2] & 0x3F]);
            }

            sindex++;

            if (sindex == 1)
            {
                pbData1[0] = pbData1[15];
            }

            if (sindex == 2)
            {
                pbData1[0] = pbData1[15];
                pbData1[1] = pbData1[16];
            }

            if (sindex == 3) sindex = 0;
        }

        if (sindex > 0)
        {
            for (int i = sindex; i < 3; i++)
            {
                pbData1[i] = '\0';
            }
            int j = 0;
            buffer.append(convertChar[pbData1[j * 3] >> 2 & 0x3F]);
            buffer.append(convertChar[(pbData1[j * 3] << 4 & 0x30)
                    + (pbData1[j * 3 + 1] >> 4 & 0x0F)]);
            buffer.append(convertChar[(pbData1[j * 3 + 1] << 2 & 0x3C)
                    + (pbData1[j * 3 + 2] >> 6 & 0x03)]);
            buffer.append(convertChar[pbData1[j * 3 + 2] & 0x3F]);
        }

        return buffer.toString();
    }


    public static void main(String[] args) throws Exception
    {
        String data = "aa1123";
        String aaa = doEncrypt(data);
        String bbb = doDecode(pdwBasicRoundKey, aaa);

        String keyFile = "/app/properties/security/seed.roundkey";
        aaa = doEncrypt(data, keyFile);
        bbb = doDecode(aaa, keyFile);
    }
}
