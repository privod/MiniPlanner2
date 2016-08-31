package ru.home.miniplanner2.view.edit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.home.miniplanner2.db.Dao;
import ru.home.miniplanner2.db.HelperFactory;
import ru.home.miniplanner2.model.Domain;
import ru.home.miniplanner2.model.Plan;

/**
 * Created by privod on 31.08.2016.
 */
public abstract class EditFragment<T extends Domain> extends Fragment {

    private static final String ARG_ID = "id";

    protected Dao<T> dao;
    protected long id;
    protected T entity;

    public static <F extends EditFragment> F newInstance(F fragment, long id) {
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public abstract T newInstanceEntity();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(ARG_ID);
        }

        if (id == 0) {
            entity = newInstanceEntity();
        } else {
            entity = dao.getById(id);
        }
    }
}