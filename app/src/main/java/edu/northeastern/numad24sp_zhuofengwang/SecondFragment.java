package edu.northeastern.numad24sp_zhuofengwang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.northeastern.numad24sp_zhuofengwang.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.setText("Pressed: A");
            }

        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.setText("Pressed: B");
            }

        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.setText("Pressed: C");
            }

        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.setText("Pressed: D");
            }

        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.setText("Pressed: E");
            }

        });

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.setText("Pressed: F");
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}