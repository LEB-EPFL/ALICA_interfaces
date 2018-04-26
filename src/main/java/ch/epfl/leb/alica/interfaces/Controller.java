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

import ch.epfl.leb.alica.interfaces.controllers.ControllerStatusPanel;


/**
 * A Controller receives the output of the Analyzer as input, and then
 * adjusts its internal state accordingly. It can be asked for output
 * at any time by the WorkerThread.
 * @author Marcel Stefko
 */
public interface Controller {
    /**
     * Sets new_setpoint value
     * @param new_setpoint desired input signal value (setpoint)
     */
    public void setSetpoint(double new_setpoint);
    
    /**
     * @return current setpoint value
     */
    public double getSetpoint();
    
    /**
     * Receives next input from the WorkerThread (this the batched output of the
     * analyzer).
     * @param value input to be processed. This input can be Double.NaN, if 
     * the Analyzer can not produce an output for some reason.
     * @return same as getCurrentOutput()
     */
    public double nextValue(double value);
    
    /**
     * Produce an output value based on internal state (the output value is
     * fed into the laser)
     * @return output value (desired laser power)
     */
    public double getCurrentOutput();
    
    /**
     *
     * @return unique name of the controller
     */
    public String getName();
    
    /**
     *
     * @return status panel to be placed into the MonitorGUI, or null
     */
    public ControllerStatusPanel getStatusPanel();
}
