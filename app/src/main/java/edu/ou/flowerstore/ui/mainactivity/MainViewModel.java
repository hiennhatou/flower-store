package edu.ou.flowerstore.ui.mainactivity;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import edu.ou.flowerstore.ui.Navigation;

public class MainViewModel extends BaseObservable {
    private int core = 1;

    @Bindable
    public String getCore() {
        return Integer.toString(core);
    }

    @Bindable
    public void setCore(int core) {
        this.core = core;
        notifyPropertyChanged(BR.core);
    }

    @Bindable
    public void setCoreByPlus(int core) {
        setCore(this.core + core);
    }

    @Bindable
    public void setContext(Context context) {
        Navigation.toSubActivity(context);
    }
}
