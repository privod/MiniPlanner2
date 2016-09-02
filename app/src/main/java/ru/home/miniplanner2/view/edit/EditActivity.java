package ru.home.miniplanner2.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.db.Dao;
import ru.home.miniplanner2.model.Domain;


/**
 * Created by privod on 27.10.2015.
 */
public abstract class EditActivity<T extends Domain> extends AppCompatActivity {
    static final String LOG_TAG = EditActivity.class.getSimpleName();
    static final String ARG_ID = "id";

    private Dao<T> dao;
    protected T entity;

    protected OnEditorActionDoneListener doneListener;

    protected Button okButton;
    protected Button cancelButton;

    public abstract T newInstanceEntity();
    public abstract void changeEntity();                    // TODO change method name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = this.getIntent();
        long id = (long) intent.getLongExtra(getString(R.string.argument_id), 0);
        if (id == 0) {
            entity = newInstanceEntity();
        } else {
            entity = dao.getById(id);
        }

        okButton = (Button) findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEntity();
                dao.save(entity);
                setResult(RESULT_OK);
                finish();
            }
        });

        cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
