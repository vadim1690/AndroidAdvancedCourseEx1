package com.vadim.dev.mobile.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class AppUtils {

    private static AppUtils INSTANCE;
    private Application context;

    public AppUtils(Application context) {
        this.context = context;
    }

    public interface AlertDialogCallback {
        void onPositiveButtonClicked();

        void onNegativeButtonClicked();
    }

    public static AppUtils init(Application context) {
        if (INSTANCE == null) {
            INSTANCE = new AppUtils(context);
        }
        return INSTANCE;
    }

    public static AppUtils getInstance() {
        return INSTANCE;
    }


    public static void showAlertDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, null)
                .create()
                .show();
    }

    public static void showAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton(android.R.string.yes, null)
                .create()
                .show();
    }

    public static void showAlertDialog(Context context, String message, String title, AlertDialogCallback callback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    if (callback != null)
                        callback.onPositiveButtonClicked();
                })
                .setNegativeButton(android.R.string.no, (dialogInterface, i) -> {
                    if (callback != null)
                        callback.onNegativeButtonClicked();
                })
                .show();

    }


    public static void showAlertDialog(Context context, String message, AlertDialogCallback callback) {
        new AlertDialog.Builder(context)
                .setTitle("Warning")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    if (callback != null)
                        callback.onPositiveButtonClicked();
                })
                .setNegativeButton(android.R.string.no, (dialogInterface, i) -> {
                    if (callback != null)
                        callback.onNegativeButtonClicked();
                })
                .show();

    }


    /**
     * formats the double value to integer if it is an integer.
     * for example: 1.00 - returns 1 ,  1.32 returns 1.32
     *
     * @param value double value to format
     * @return formatted string value.
     */
    public static String formatDoubleWithDecimal(double value) {
        if (value % 1 == 0) {
            return String.valueOf((int) value);
        }

        String text = Double.toString(Math.abs(value));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;
        return decimalPlaces > 3 ? String.format(Locale.getDefault(), "%.3f", value) : String.valueOf(value);
    }

    /**
     * formats the double value to integer if it is an integer.
     * for example: 1.00 - returns 1 ,  1.32 returns 1.32
     *
     * @param stringValue double value to format
     * @return formatted string value.
     */
    public static String formatDoubleWithDecimal(String stringValue) {
        if (stringValue == null) return "0";
        double value = Double.parseDouble(stringValue);
        if (value % 1 == 0) {
            return String.valueOf((int) value);
        }

        String text = Double.toString(Math.abs(value));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;
        return decimalPlaces > 3 ? String.format(Locale.getDefault(), "%.3f", value) : String.valueOf(value);
    }


    // Function to open WhatsApp with the given phone number
    public static void openWhatsApp(Activity activity, String phoneNumber) {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber));
            activity.startActivity(intent);
        } catch (Exception e) {
            showToast(activity, e.getMessage());
        }

    }

    /**
     * Hides the soft keyboard.
     *
     * @param context The context.
     * @param view    The view that the keyboard should be hidden for.
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Shows a short toast message.
     *
     * @param context The context.
     * @param message The message to show.
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a long toast message.
     *
     * @param context The context.
     * @param message The message to show.
     */
    public static void showToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Launches the email app with a new email pre-populated with the specified subject and recipient.
     *
     * @param context   The context.
     * @param recipient The email address of the recipient.
     * @param subject   The subject of the email.
     */
    public static void sendEmail(Context context, String recipient, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Launches the phone app with the specified phone number.
     *
     * @param activity    The context.
     * @param phoneNumber The phone number to call.
     */
    public static void makePhoneCall(Activity activity, String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            activity.startActivity(intent);
        } catch (Exception e) {
            showToast(activity, e.getMessage());
        }

    }

    /**
     * Returns the screen width in pixels.
     *
     * @param activity The activity.
     * @return The screen width in pixels.
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * Returns the screen height in pixels.
     *
     * @param activity The activity.
     * @return The screen height in pixels.
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    /**
     * Converts a Drawable to a Bitmap.
     *
     * @param drawable The Drawable to convert.
     * @return The Bitmap.
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Changes the color of a Drawable.
     *
     * @param drawable The Drawable to change the color of.
     * @param color    The new color.
     * @return The Drawable with the new color.
     */
    public static Drawable changeDrawableColor(Drawable drawable, int color) {
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    /**
     * Saves a Bitmap to a file.
     *
     * @param context The context.
     * @param bitmap  The Bitmap to save.
     * @param file    The file to save the Bitmap to.
     * @throws IOException if the Bitmap could not be saved.
     */
    public static void saveBitmap(Context context, Bitmap bitmap, File file) throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


}
