package ru.home.miniplanner2.view.edit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.Util;
import ru.home.miniplanner2.db.HelperFactory;
import ru.home.miniplanner2.model.Plan;

/**
 * Created by privod on 01.09.2016.
 */
public class PlanEditActivity extends EditActivity<Plan> {
    static final String LOG_TAG = PlanEditActivity.class.getSimpleName();

    private EditText nameEditText;
    private EditText dateRegEditText;

    @Override
    public Plan newInstanceEntity() {
        return new Plan();
    }

    @Override
    public void changeEntity() {
        entity.setName(nameEditText.getText().toString());
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getPlanDao();

        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_plan, layout, true);

        nameEditText = (EditText) findViewById(R.id.edit_text_name);
        dateRegEditText = (EditText) findViewById(R.id.edit_text_date);

        nameEditText.setText(entity.getName());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));

        nameEditText.requestFocus();
        nameEditText.selectAll();
        nameEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneListener));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
    }
}
