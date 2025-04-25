package com.faltenreich.diaguard.shared.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.faltenreich.diaguard.feature.navigation.ToolbarOwner;
import com.faltenreich.diaguard.feature.preference.backup.BackupImportPreference;
import com.faltenreich.diaguard.shared.data.permission.Permission;
import com.faltenreich.diaguard.shared.data.permission.PermissionManager;
import com.faltenreich.diaguard.shared.data.permission.PermissionUseCase;
import com.faltenreich.diaguard.shared.event.Events;
import com.faltenreich.diaguard.shared.event.file.FileProvidedEvent;
import com.faltenreich.diaguard.shared.event.file.FileProvidedFailedEvent;
import com.faltenreich.diaguard.shared.event.permission.PermissionRequestEvent;
import com.faltenreich.diaguard.shared.event.permission.PermissionResponseEvent;
import com.faltenreich.diaguard.shared.view.ViewBindable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity<BINDING extends ViewBinding> extends AppCompatActivity implements ViewBindable<BINDING> {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private BINDING binding;

    protected abstract BINDING createBinding(LayoutInflater layoutInflater);

    @Override
    public BINDING getBinding() {
        return binding;
    }

    @Override
    public void setTitle(CharSequence title) {
        if (this instanceof ToolbarOwner) {
            ((ToolbarOwner) this).getTitleView().setText(title);
        } else {
            super.setTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = createBinding(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Events.register(this);
    }

    @Override
    protected void onPause() {
        Events.unregister(this);
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] codes, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, codes, grantResults);
        PermissionUseCase useCase = PermissionUseCase.fromRequestCode(requestCode);
        if (useCase != null) {
            for (String code : codes) {
                Permission permission = Permission.fromCode(code);
                if (permission != null) {
                    boolean isGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    Events.post(new PermissionResponseEvent(permission, useCase, isGranted));
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BackupImportPreference.REQUEST_CODE_BACKUP_IMPORT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getData() != null) {
                    Events.post(new FileProvidedEvent(data.getData()));
                } else {
                    Events.post(new FileProvidedFailedEvent());
                }
            }
        } else {
            Log.d(TAG, "Ignoring unknown result with request code" + requestCode);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PermissionRequestEvent event) {
        if (PermissionManager.getInstance().hasPermission(this, event.context)) {
            Events.post(new PermissionResponseEvent(event.context, event.useCase, true));
        } else {
            PermissionManager.getInstance().requestPermission(this, event.context, event.useCase);
        }
    }
}