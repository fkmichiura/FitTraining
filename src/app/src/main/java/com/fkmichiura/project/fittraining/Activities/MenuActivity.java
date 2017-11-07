package com.fkmichiura.project.fittraining.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.appodeal.ads.Appodeal;
import com.fkmichiura.project.fittraining.Fragments.ProfileFragment;
import com.fkmichiura.project.fittraining.R;
import com.fkmichiura.project.fittraining.Fragments.TrainingFragment;
import com.fkmichiura.project.fittraining.Adapters.ViewPagerAdapter;
import com.fkmichiura.project.fittraining.Fragments.WeightFragment;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurações do Adaptador para atribuir uma TabLayout na Activity
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new TrainingFragment(), "Treinos");
        viewPagerAdapter.addFragments(new ProfileFragment(), "Perfil");
        viewPagerAdapter.addFragments(new WeightFragment(), "Peso");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //Atribuição de ícones aos títulos da TabLayout
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu2);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu3);
    }
}
