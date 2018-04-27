/* 
 * Copyright (C) 2017 Laboratory of Experimental Biophysics
 * Ecole Polytechnique Federale de Lausanne
 * 
 * Author: Marcel Stefko
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.epfl.leb.alica.interfaces;

import ij.IJ;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A template for factories that are set up using a JPanel.
 * 
 * This template is currently used by the Analyzer and Controller interfaces.
 * 
 * @author Marcel Stefko
 * @param <ProductSetupPanel> A JPanel for setting up the parameters.
 */
public abstract class AbstractFactory<ProductSetupPanel> {
    
    /**
     * The name of analyzer/controller.
     */
    String selected_name;
    
    /**
     * A map of names and setup panels.
     */
    private final LinkedHashMap<String, ProductSetupPanel> setup_panels;
    
    /**
     * Initializes the map which stores different setup panels.
     */
    public AbstractFactory() {
        setup_panels = new LinkedHashMap<String, ProductSetupPanel>();
    }
    
    /**
     * Adds a new setup panel to the list.
     * 
     * @param name ID of the product belonging to the panel and displayed in 
     * the GUI.
     * @param panel The JPanel to setup the analyzer/controller.
     */
    protected void addSetupPanel(String name, ProductSetupPanel panel) {
        if (setup_panels.containsKey(name)) {
            String message = "Unable to load " + name + " because such panel already exists.";
            Logger.getLogger("Loading ALICA_ACpack").log(Level.SEVERE, message);
            return;
        }
        setup_panels.put(name, panel);
    }
    
    /**
     * Returns the name of the selected analyzer/controller.
     * 
     * @return The name of currently selected product.
     */
    public String getSelectedProductName() {
        return selected_name;
    }
    
    /**
     * Returns a list of all possible analyzer/controller names.
     * 
     * @return A list of all possible product keys.
     */
    public Set<String> getProductNameList() {
        return setup_panels.keySet();
    }
    
    /**
     * Returns a group of all the possible setup panels for the available analyzers/controllers.
     * 
     * @return A collection of all possible product setup panels.
     */
    public Collection<ProductSetupPanel> getProductSetupPanelCollection() {
        return setup_panels.values();
    }
    
    /**
     * Selects a product from factory.
     * 
     * @param name The product identifier.
     */
    public void selectProduct(String name) {
        if (name == null) {
            selected_name = null;
            return;
        }
        if (!setup_panels.containsKey(name)) {
            throw new IllegalArgumentException("No such product: "+name);
        }
        selected_name = name;
    }
    
    /**
     * Returns the setup panel of the currently selected analyzer/controller.
     * 
     * @return The setup panel of currently selected analyzer/product.
     */
    public ProductSetupPanel getSelectedSetupPanel() {
        if (selected_name==null)
            return null;
        return setup_panels.get(selected_name);
    }
}
