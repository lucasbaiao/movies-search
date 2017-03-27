package lucasbaiao.com.br.moviessearch.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import lucasbaiao.com.br.moviessearch.view.BaseActivity;

public class BaseFragment extends Fragment {

    public void setTitle(String title) {
        ActionBar actionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setTitle(int title) {
        this.setTitle(getActivity().getApplicationContext().getResources().getString(title));
    }
}
