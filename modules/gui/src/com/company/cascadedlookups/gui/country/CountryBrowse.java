/*
 * Copyright (c) 2016 cascaded-lookups
 */
package com.company.cascadedlookups.gui.country;

import com.company.cascadedlookups.entity.Country;
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
public class CountryBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<Country, UUID> countriesDs;

    @Inject
    private Datasource<Country> countryDs;

    @Inject
    private Table<Country> countriesTable;

    @Inject
    private BoxLayout lookupBox;

    @Inject
    private BoxLayout actionsPane;

    @Inject
    private FieldGroup fieldGroup;

    

    @Named("countriesTable.remove")
    private RemoveAction countriesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {
        countriesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Country reloadedItem = dataSupplier.reload(e.getDs().getItem(), countryDs.getView());
                countryDs.setItem(reloadedItem);
            }
        });

        countriesTable.addAction(new CreateAction(countriesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                countriesTable.setSelected(Collections.emptyList());
                countryDs.setItem((Country) newItem);
                enableEditControls(true);
            }
        });

        countriesTable.addAction(new EditAction(countriesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (countriesTable.getSelected().size() == 1) {
                    enableEditControls(false);
                }
            }
        });

        countriesTableRemove.setAfterRemoveHandler(removedItems -> countryDs.setItem(null));

        disableEditControls();
    }

    public void save() {
        getDsContext().commit();

        Country editedItem = countryDs.getItem();
        if (creating) {
            countriesDs.includeItem(editedItem);
        } else {
            countriesDs.updateItem(editedItem);
        }
        countriesTable.setSelected(editedItem);

        disableEditControls();
    }

    public void cancel() {
        Country selectedItem = countriesDs.getItem();
        if (selectedItem != null) {
            Country reloadedItem = dataSupplier.reload(selectedItem, countryDs.getView());
            countriesDs.setItem(reloadedItem);
        } else {
            countryDs.setItem(null);
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
        countriesTable.requestFocus();
    }

    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}