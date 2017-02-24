package com.sds.acube.app.login.security.seed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MakeRoundKey
{
    public static void main(String[] args) throws Exception
    {
        String roundkeyFile = "seed.roundkey";
        if (args.length > 0)
        {
            roundkeyFile = args[0];
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String userkey = br.readLine();

        int pdwRoundKey[] = new int[32];
        byte pbUserKey[] = {
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
        };

        byte[] tmpUserkey = userkey.getBytes();
        for (int i = 0; i < (tmpUserkey.length > 16 ? 16 : tmpUserkey.length); i++)
        {
            pbUserKey[i] = tmpUserkey[i];
        }

        Seedx.SeedEncRoundKey(pdwRoundKey, pbUserKey);

        StringBuffer roundkeyStmt = new StringBuffer();
        for (int i = 0; i < pdwRoundKey.length; i++)
        {
            if (i > 0) roundkeyStmt.append(",");
            roundkeyStmt.append("" + pdwRoundKey[i]);
        }

        String roundkeyCert = SeedBean.doEncrypt(roundkeyStmt.toString());

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                roundkeyFile)));
        out.write(roundkeyCert);
        out.close();
    }
}
