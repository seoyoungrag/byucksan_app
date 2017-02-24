package com.sds.acube.app.login.security.seed;


/***************************************************************************************************
 * Made : 2006. 7.18. Update : 2006.11.24 FILE : seedx_test.java DESCRIPTION: Core routines for the
 * enhanced SEED
 **************************************************************************************************/

public class SeedxTest
{

    public static void main(String[] args)
    {
        test1();
//        test2();
    }


    public static void test1()
    {
        int pdwRoundKey[] = new int[32];
        byte pbUserKey[] = {
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
        };
        byte pbData[] = {
                (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05,
                (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F
        };
        pbData = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes(); 

        byte pbCipher[] = new byte[pbData.length];
        byte pbPlain[] = new byte[pbData.length];

        Seedx.SeedEncRoundKey(pdwRoundKey, pbUserKey);


        System.out.print("[ Test SEED reference code ]" + "\n");
        System.out.print("\n\n");

        // 암호화...
        Seedx.SeedEncrypt(pbData, pdwRoundKey, pbCipher);

        System.out.print("[ Test Encrypt mode ]" + "\n");
        System.out.print("Key\t\t: ");
        printByteArray(pbUserKey);
        System.out.print("\n");

        System.out.print("Plaintext\t: ");
        printByteArray(pbData);
        System.out.print("\n");

        System.out.print("Ciphertext\t: ");
        printByteArray(pbCipher);
        System.out.print("\n\n");

        // 복호화...
        Seedx.SeedDecrypt(pbCipher, pdwRoundKey, pbPlain);

        System.out.print("[ Test Decrypt mode ]" + "\n");
        System.out.print("Key\t\t: ");
        printByteArray(pbUserKey);
        System.out.print("\n");

        System.out.print("Ciphertext\t: ");
        printByteArray(pbCipher);
        System.out.print("\n");

        System.out.print("Plaintext\t: ");
        printByteArray(pbPlain);
        System.out.print("\n\n");
    }


    public static void test2()
    {
        int pdwRoundKey[] = new int[32];

        String pbUserKey = "qwertyuiopasdfga";

        String pbData = "aaa 한글 나는 한글aaaaaa";

        byte pbCipher[] = new byte[pbData.getBytes().length];
        byte pbPlain[] = new byte[pbData.getBytes().length];

        System.out.print("[ Test SEED reference code ]" + "\n");
        System.out.print("\n\n");

        // 라운드키 설정
        Seedx.SeedEncRoundKey(pdwRoundKey, pbUserKey.getBytes());

        // 암호화...
        Seedx.SeedEncrypt(pbData.getBytes(), pdwRoundKey, pbCipher);

        System.out.print("[ Test Encrypt mode ]" + "\n");
        System.out.print("Key\t\t: ");
        printStringByte(pbUserKey);
        System.out.print("\n");
        System.out.print("Plaintext\t: ");
        printStringByte(pbData);
        System.out.print("\n");

        System.out.print("Ciphertext\t: ");
        printByteArray(pbCipher);
        System.out.print("\n\n");

        // 복호화...
        Seedx.SeedDecrypt(pbCipher, pdwRoundKey, pbPlain);

        System.out.print("[ Test Decrypt mode ]" + "\n");
        System.out.print("Key\t\t: ");
        printStringByte(pbUserKey);
        System.out.print("\n");

        System.out.print("Ciphertext\t: ");
        printByteArray(pbCipher);
        System.out.print("\n");

        System.out.print("Plaintext\t: ");
        printByteArray(pbPlain);
        System.out.print("\n\n");
    }


    private static void printByteArray(byte[] srcByte)
    {
        for (int i = 0; i < srcByte.length; i++)
        {
            String hex = Integer.toHexString(0xff & srcByte[i]);
            if (hex.length() == 1)
            {
                System.out.print("0" + hex + " ");
            }
            else
            {
                System.out.print(hex + " ");
            }
        }
    }


    private static void printStringByte(String src)
    {
        byte[] tmpByte = src.getBytes();
        for (int i = 0; i < tmpByte.length; i++)
        {
            String hex = Integer.toHexString(0xff & tmpByte[i]);
            if (hex.length() == 1)
            {
                System.out.print("0" + hex + " ");
            }
            else
            {
                System.out.print(hex + " ");
            }
        }
    }
}
