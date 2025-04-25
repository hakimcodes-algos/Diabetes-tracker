package com.faltenreich.diaguard.shared.view.progress;

import android.app.ProgressDialog;
import android.content.Context;

import com.faltenreich.diaguard.R;

public class ProgressComponent {

    private ProgressDialog progressDialog;

    public ProgressComponent() {

    }

    public void show(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.backup_import_progress));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void setMessage(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
        }
    }

    public void dismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
