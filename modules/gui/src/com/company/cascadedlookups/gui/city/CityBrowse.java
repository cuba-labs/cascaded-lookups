/*
 * Copyright (c) 2016 cascaded-lookups
 */
package com.company.cascadedlookups.gui.city;

import com.company.cascadedlookups.entity.City;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.ComponentsHelper;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * @author knst
 */
public class CityBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<City, UUID> citiesDs;

    @Inject
    private Datasource<City> cityDs;

    @Inject
    private Table<City> citiesTable;

    @Inject
    private BoxLayout lookupBox;

    @Inject
    private BoxLayout actionsPane;

    @Inject
    private FieldGroup fieldGroup;

    

    @Named("citiesTable.remove")
    private RemoveAction citiesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {
        citiesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                City reloadedItem = dataSupplier.reload(e.getDs().getItem(), cityDs.getView());
                cityDs.setItem(reloadedItem);
            }
        });

        citiesTable.addAction(new CreateAction(citiesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                citiesTable.setSelected(Collections.emptyList());
                cityDs.setItem((City) newItem);
                enableEditControls(true);
            }
        });

        citiesTable.addAction(new EditAction(citiesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (citiesTable.getSelected().size() == 1) {
                    enableEditControls(false);
                }
            }
        });

        citiesTableRemove.setAfterRemoveHandler(removedItems -> cityDs.setItem(null));

        disableEditControls();
    }

    public void save() {
        getDsContext().commit();

        City editedItem = cityDs.getItem();
        if (creating) {
            citiesDs.includeItem(editedItem);
        } else {
            citiesDs.updateItem(editedItem);
        }
        citiesTable.setSelected(editedItem);

        disableEditControls();
    }

    public void cancel() {
        City selectedItem = citiesDs.getItem();
        if (selectedItem != null) {
            City reloadedItem = dataSupplier.reload(selectedItem, cityDs.getView());
            citiesDs.setItem(reloadedItem);
        } else {
            cityDs.setItem(null);
        }

        disableEditControls();
    }

    private void enableEditControls(boolean creating) {
        this.creating = creating;
        initEditComponents(true);
        fieldGroup.requestFocus();
    }

    private void disableEditControls() {
        initEditComponents(false);
        citiesTable.requestFocus();
    }

    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}