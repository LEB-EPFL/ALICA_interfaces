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

import ch.epfl.leb.alica.interfaces.controllers.ControllerStatusPanel;


/**
 * Adjusts control parameters in response to values provided by the analyzer.
 * 
 * A controller receives the output of the analyzer as input, and then adjusts
 * its internal state accordingly. It can be asked for its state at any time by
 * the WorkerThread.
 * 
 * @author Marcel Stefko
 */
public interface Controller {
    /**
     * Sets a new values for the controller's setpoint.
     * 
     * @param new_setpoint The desired value for the process variable.
     */
    public void setSetpoint(double new_setpoint);
    
    /**
     * Returns the current value for the setpoint.
     * 
     * @return The current setpoint value.
     */
    public double getSetpoint();
    
    /**
     * Receives next input from the WorkerThread (this the batched output of the
     * analyzer).
     * 
     * This method must be able to accept Double.NaN, which the analyzer will
     * produce if if can not produce an output for some reason.
     * 
     * @param value The analyzer's input value.
     * @return The controller's new output value for the control signal.
     */
    public double nextValue(double value);
    
    /**
     * Returns an output value derived from the controller's internal state.
     * 
     * @return The controller's new output value for the control signal.
     */
    public double getCurrentOutput();
    
    /**
     * Returns a unique name for the controller.
     * 
     * @return A unique name for the controller.
     */
    public String getName();
    
    /**
     * Returns the controller's status panel that will be displayed in the GUI.
     * 
     * If no panel is implemented, this method should return null. In this case, 
     * the corresponding space in the MonitorGUI will appear blank.
     * 
     * @return The status panel of the controller, or null.
     */
    public ControllerStatusPanel getStatusPanel();
}
