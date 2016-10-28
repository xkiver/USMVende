package cl.telematica.android.usmvende;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Comprador extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManagerComprador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprador);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManagerComprador = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerComprador);
        adapter = new MyAdapterComprador(getListProduct());
        recyclerView.setAdapter(adapter);

    }

    public List<Producto> getListProduct(){
        List<Producto> producto = new ArrayList<>();
        Producto  p = new Producto();
        for(int i = 0; i < 4; i++) {
            p.setDescripcion("Buena calidad"+i);
            p.setNombreP("Usuario"+i);
            p.setPrecio("200"+i);
            producto.add(p);
        }
        return producto;
    }
}
