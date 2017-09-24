package com.example.android.ersatz.utils;


import android.graphics.Bitmap;

import net.glxn.qrgen.android.QRCode;

public class QrUtils {

    public static Bitmap makeBitmapQrCodeFromUrl(String url) {
        // TODO: configure colors
        return QRCode.from(url).withSize(480, 480).bitmap();
    }

}
