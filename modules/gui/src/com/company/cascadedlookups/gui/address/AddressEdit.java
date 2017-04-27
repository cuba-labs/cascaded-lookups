package com.company.cascadedlookups.gui.address;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.cascadedlookups.entity.Address;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import java.util.Map;

public class AddressEdit extends AbstractEditor<Address> {

    @Inject
    protected Datasource<Address> addressDs;

    @Override
    public void init(Map<String, Object> params) {
        addressDs.addItemPropertyChangeListener(e -> {
            if (e.getProperty().equals("country")) {
                addressDs.getItem().setCity(null);
            }
        });
    }
}