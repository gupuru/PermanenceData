package com.gupuru.permanencedata;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * データを外部公開領域に読み書きする ライブラリ
 */
public class PermanenceData {

    private static final String DIRECTORY_NAME = "com.gupuru.permanence_data_library";
    private static final String FILE_NAME = "data.txt";

    public PermanenceData() {
    }

    /**
     * データ保存
     *
     * @param data
     * @return
     */
    public boolean save(String data) {
        if (data == null || data.equals("")) {
            return false;
        }

        if (makeDirectory(DIRECTORY_NAME)) {
            if (makeFile(DIRECTORY_NAME, FILE_NAME, data.getBytes())) {
                return true;
            }
        }

        return false;
    }

    /**
     * データ保存 ディレクトリ名, ファイル名指定
     *
     * @param directoryName
     * @param fileName
     * @param data
     * @return
     */
    public boolean save(String directoryName, String fileName, String data) {
        if (data == null || data.equals("")) {
            return false;
        }

        if (makeDirectory(directoryName)) {
            if (makeFile(directoryName, fileName, data.getBytes())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 保存データ読み込み
     *
     * @return
     */
    public String read() {
        return readData(DIRECTORY_NAME, FILE_NAME);
    }

    /**
     * 保存データ読み込み
     *
     * @return
     */
    public String read(String directoryName, String fileName) {
        return readData(directoryName, fileName);
    }

    /**
     * データ削除
     * @return
     */
    public boolean delete() {
        File file = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + DIRECTORY_NAME + File.separator);
        return deleteDirectory(file);
    }

    /**
     * ディレクトリを削除する
     *
     * @param directoryName
     * @param fileName
     * @return
     */
    public boolean delete(String directoryName, String fileName) {
        if (fileName == null || fileName.equals("")) {
            fileName = FILE_NAME;
        }
        if (directoryName == null || directoryName.equals("")) {
            directoryName = DIRECTORY_NAME;
        }
        File file = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + directoryName, fileName
        );

        return deleteDirectory(file);
    }

    /**
     * データを読み込み 取得できない場合はnullを返す
     *
     * @param directoryName
     * @param fileName
     * @return
     */
    private String readData(String directoryName, String fileName) {
        if (fileName == null || fileName.equals("")) {
            fileName = FILE_NAME;
        }
        if (directoryName == null || directoryName.equals("")) {
            directoryName = DIRECTORY_NAME;
        }

        File file = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + directoryName, fileName
        );

        if (file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();

            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append('\n');
                }
                bufferedReader.close();

                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 保存ディレクトリ作成 true -> 作成, false -> 失敗
     *
     * @param directoryName
     * @return
     */
    private boolean makeDirectory(String directoryName) {
        if (directoryName == null || directoryName.equals("")) {
            directoryName = DIRECTORY_NAME;
        }
        File directory = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + directoryName + File.separator
        );
        //ディレクトリがない -> ディレクトリ作成
        if (!directory.exists() && !directory.isDirectory()) {
            return directory.mkdir();
        } else {
            return true;
        }
    }

    /**
     * fileを削除する
     *
     * @param file
     * @return
     */
    private boolean deleteDirectory(File file) {
        Runtime localRuntime = Runtime.getRuntime();
        String cmd = "rm -R " + file.toString();
        try {
            //削除実行
            localRuntime.exec(cmd);
            return true;
        } catch (IOException e) {
            //エラー
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ファイル作成 true -> 作成, false -> 失敗
     *
     * @param directoryName
     * @param fileName
     * @param data
     * @return
     */
    private boolean makeFile(String directoryName, String fileName, byte[] data) {
        if (fileName == null || fileName.equals("")) {
            fileName = FILE_NAME;
        }
        if (directoryName == null || directoryName.equals("")) {
            directoryName = DIRECTORY_NAME;
        }

        FileOutputStream outputStream;

        File file = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + directoryName, fileName
        );

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
