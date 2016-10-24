package cl.telematica.android.usmvende;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {

    Button btnComprar, btnVender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnComprar = (Button) findViewById(R.id.btnComprador);
        btnVender = (Button) findViewById(R.id.btnVendedor);

        btnVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this,RegistroProducto.class);
                startActivity(intent);
            }
        });

    }
}
