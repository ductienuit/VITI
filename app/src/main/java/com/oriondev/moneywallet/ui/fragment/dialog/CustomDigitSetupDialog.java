/*
 * Copyright (c) 2019.
 *
 * This file is part of Viti.
 *
 * Viti is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Viti is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Viti.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.oriondev.Viti.ui.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.Viti.R;
import com.oriondev.Viti.model.CurrencyUnit;
import com.oriondev.Viti.storage.preference.PreferenceManager;
import com.oriondev.Viti.ui.view.theme.ThemedDialog;
import com.oriondev.Viti.utils.CurrencyManager;
import com.oriondev.Viti.utils.MoneyFormatter;

/**
 * Created by DucTien on 12/10/2019.
 */
public class CustomDigitSetupDialog extends DialogFragment {

    private static final String SS_CURRENCY_ENABLED = "CustomDigitSetupDialog::SavedState::CurrencyEnabled";
    private static final String SS_GROUPING_ENABLED = "CustomDigitSetupDialog::SavedState::GroupingEnabled";
    private static final String SS_ROUNDING_ENABLED = "CustomDigitSetupDialog::SavedState::RoundingEnabled";
    private static final String SS_SYMBOL_ENABLED = "CustomDigitSetupDialog::SavedState::SymbolEnabled";

    private static final CurrencyUnit DEFAULT_CURRENCY = CurrencyManager.getDefaultCurrency();
    private static final long DEFAULT_MONEY = 1258972L;

    private TextView mDisplayTextView;

    private MoneyFormatter mFormatter;

    public static CustomDigitSetupDialog newInstance() {
        return new CustomDigitSetupDialog();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        MaterialDialog dialog = ThemedDialog.buildMaterialDialog(activity)
                .customView(R.layout.layout_dialog_custom_digit_setup, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onSaveChanges();
                    }

                })
                .build();
        View view = dialog.getCustomView();
        if (view == null) {
            throw new IllegalStateException("Custom view has been inflated but the returned view is null");
        }
        mDisplayTextView = view.findViewById(R.id.display_text_view);
        SwitchCompat currencyEnabledSwitch = view.findViewById(R.id.show_currency_switch);
        SwitchCompat groupingEnabledSwitch = view.findViewById(R.id.group_digits_switch);
        SwitchCompat roundingEnabledSwitch = view.findViewById(R.id.round_decimals_switch);
        SwitchCompat symbolEnabledSwitch = view.findViewById(R.id.show_always_plus_minus_switch);
        // setup formatter and load default or saved settings
        mFormatter = MoneyFormatter.getInstance();
        if (savedInstanceState != null) {
            mFormatter.setCurrencyEnabled(savedInstanceState.getBoolean(SS_CURRENCY_ENABLED));
            mFormatter.setGroupDigitEnabled(savedInstanceState.getBoolean(SS_GROUPING_ENABLED));
            mFormatter.setRoundDecimalsEnabled(savedInstanceState.getBoolean(SS_ROUNDING_ENABLED));
            mFormatter.setShowSymbolEnabled(savedInstanceState.getBoolean(SS_SYMBOL_ENABLED));
        }
        currencyEnabledSwitch.setChecked(mFormatter.isCurrencyEnabled());
        groupingEnabledSwitch.setChecked(mFormatter.isGroupDigitEnabled());
        roundingEnabledSwitch.setChecked(mFormatter.isRoundDecimalsEnabled());
        symbolEnabledSwitch.setChecked(mFormatter.isShowSymbolEnabled());
        // attach listeners
        currencyEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFormatter.setCurrencyEnabled(isChecked);
                refreshDisplay();
            }

        });
        groupingEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFormatter.setGroupDigitEnabled(isChecked);
                refreshDisplay();
            }

        });
        roundingEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFormatter.setRoundDecimalsEnabled(isChecked);
                refreshDisplay();
            }

        });
        symbolEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFormatter.setShowSymbolEnabled(isChecked);
                refreshDisplay();
            }

        });
        // refresh the current display
        refreshDisplay();
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SS_CURRENCY_ENABLED, mFormatter.isCurrencyEnabled());
        outState.putBoolean(SS_GROUPING_ENABLED, mFormatter.isGroupDigitEnabled());
        outState.putBoolean(SS_ROUNDING_ENABLED, mFormatter.isRoundDecimalsEnabled());
        outState.putBoolean(SS_SYMBOL_ENABLED, mFormatter.isShowSymbolEnabled());
    }

    private void refreshDisplay() {
        mFormatter.applyNotTinted(mDisplayTextView, DEFAULT_CURRENCY, DEFAULT_MONEY);
    }

    private void onSaveChanges() {
        PreferenceManager.setCurrencyEnabled(mFormatter.isCurrencyEnabled());
        PreferenceManager.setGroupDigitsEnabled(mFormatter.isGroupDigitEnabled());
        PreferenceManager.setRoundDecimalsEnabled(mFormatter.isRoundDecimalsEnabled());
        PreferenceManager.setShowPlusMinusSymbolEnabled(mFormatter.isShowSymbolEnabled());
    }
}