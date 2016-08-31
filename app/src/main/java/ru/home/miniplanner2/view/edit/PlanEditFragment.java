package ru.home.miniplanner2.view.edit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.Util;
import ru.home.miniplanner2.db.Dao;
import ru.home.miniplanner2.db.HelperFactory;
import ru.home.miniplanner2.model.Plan;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlanEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlanEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanEditFragment extends EditFragment<Plan> {

    private EditText nameEditText;
    private EditText dateRegEditText;

    public static PlanEditFragment newInstance(long id) {
        return EditFragment.newInstance(new PlanEditFragment(), id);
    }

    @Override
    public Plan newInstanceEntity() {
        return new Plan();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getPlanDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plan_edit, container, false);

        nameEditText = (EditText) view.findViewById(R.id.edit_text_name);
        dateRegEditText = (EditText) view.findViewById(R.id.edit_text_date);

        nameEditText.setText(entity.getName());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));

        nameEditText.requestFocus();
        nameEditText.selectAll();

        return view;
    }
}
