package edu.northeastern.numad24sp_zhuofengwang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.northeastern.numad24sp_zhuofengwang.databinding.FragmentFirstBinding;
import edu.northeastern.numad24sp_zhuofengwang.linkActivity.LinkActivity;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), InfoActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }

        });

        binding.ClickyClicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.LinkCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LinkActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        binding.PrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PrimeActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        binding.locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LocationActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}