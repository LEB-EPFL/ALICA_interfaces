/* 
 * Copyright (C) 2017-2018 Laboratory of Experimental Biophysics
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

import ch.epfl.leb.alica.interfaces.analyzers.AnalyzerStatusPanel;
import ij.gui.Roi;


/**
 * Processes an image or series of images.
 * 
 * Analyzers compute quantities from an image or series of images that are
 * relevant for real-time control. These quantities can include, for example,
 * the number of fluorescent molecules or the resolution.
 * 
 * @author Marcel Stefko
 */
public interface Analyzer {

    /**
     * Processes an image and adjust the analyzer's internal state to reflect the results of the calculation.
     * 
     * This method is called after each new image acquisition by the
     * AnalysisWorker. You can use the synchronized(this) statement within the
     * body of an implementation of an Analyzer to ensure that no output readout
     * happens during code execution.
     * 
     * When implementing new Analyzers, try to keep the computation time as
     * short as possible.
     * 
     * @param image The image to be processed as 1D raw pixel data.
     * @param image_width Image width in pixels.
     * @param image_height Image height in pixels.
     * @param pixel_size_um Length of a side of a square pixel in micrometers.
     * @param time_ms Image acquisition time in milliseconds.
     */
    public void processImage(
            Object image,
            int image_width,
            int image_height,
            double pixel_size_um,
            long time_ms
    );
    
    /**
     * Returns the intermittent output of the analyzer.
     * 
     * This is used to read the current output of the analyzer; its internal
     * state is unchanged.
     * 
     * {@link #getBatchOutput() getBatchOutput}() should be used to obtain output
     * that will be passed to the controller.
     * 
     * @return The analyzer's current output value.
     */
    public double getIntermittentOutput();
    
    /**
     * Returns the output of the analyzer for Controller input.
     * 
     * If no output can be provided (such as when there are no new analyzed
     * images), Double.NaN should be returned. This method can also modify the
     * internal state of the analyzer (such as flushing the intermittent value
     * cache).
     * 
     * @return The analyzer's current output value for use by the controller
     * @see ch.epfl.leb.alica.interfaces.Controller
     */
    public double getBatchOutput();
    
    /**
     * Sets the region of interest in the image so that only a portion of the whole image is analyzed.
     * 
     * @param roi The Roi object corresponding to the region.
     * @see ij.gui.Roi
     */
    public void setROI(Roi roi);
    
    /**
     * Close all windows that were opened and are owned by this analyzer.
     */
    public void dispose();
    
    /**
     * Returns a unique name for the analyzer.
     * 
     * 
     * @return The analyzer's name.
     */
    public String getName();
    
    /**
     * Returns the analyzer's status panel that will be displayed in the GUI.
     * 
     * If no panel is implemented, this method should return null. In this case, 
     * the corresponding space in the MonitorGUI will appear blank.
     *
     * @return The status panel of the analyzer, or null.
     */
    public AnalyzerStatusPanel getStatusPanel();
    
    /**
     * Returns a short description of the values returned by the analyzer.
     * 
     * @return A short description of the values returned by the analyzer.
     */
    public String getShortReturnDescription();
    
}
