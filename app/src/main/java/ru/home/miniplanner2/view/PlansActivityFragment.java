package ru.home.miniplanner2.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.home.miniplanner2.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlansActivityFragment extends Fragment {

    public PlansActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plans, container, false);


    }
}
