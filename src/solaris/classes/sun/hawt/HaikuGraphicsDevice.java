/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sun.hawt;

import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.util.ArrayList;

public class HaikuGraphicsDevice extends GraphicsDevice {

    private final int displayID;

    private final HaikuGraphicsConfig config;

    public HaikuGraphicsDevice(int displayID) {
        this.displayID = displayID;
        config = new HaikuGraphicsConfig(this);
    }

    public int getDisplayID() {
        return displayID;
    }

    @Override
    public GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] { config };
    }

    @Override
    public GraphicsConfiguration getDefaultConfiguration() {
        return config;
    }

    private static native double nativeGetXResolution(int displayID);
    private static native double nativeGetYResolution(int displayID);
    private static native DisplayMode nativeGetDisplayMode(int displayID);
    private static native void nativeGetDisplayModes(int displayID,
    	ArrayList<DisplayMode> modes);
    private static native void nativeSetDisplayMode(int displayID,
		int width, int height, int displayMode);


    @Override
    public String getIDstring() {
        return "Display " + this.displayID;
    }

    @Override
    public int getType() {
        return TYPE_RASTER_SCREEN;
    }

	@Override
	public boolean isDisplayChangeSupported() {
		return true;
	}

    @Override
    public boolean isFullScreenSupported() {
        return false;
    }

	// fold getXResolution and getYResolution into getScreenResolution
    public double getXResolution() {
    	double res = nativeGetXResolution(displayID);
    	return res == 0.0 ? 72.0 : res;
    }

    public double getYResolution() {
    	double res = nativeGetYResolution(displayID);
    	return res == 0.0 ? 72.0 : res;
    }

	public double getScreenResolution() {
		double res = (getYResolution() + getXResolution()) / 2;
		return res == 0.0 ? 72.0 : res;
	}

    @Override
    public DisplayMode getDisplayMode() {
    	// todo ?
    	return null;
    }
    
    @Override
    public DisplayMode[] getDisplayModes() {
    	// todo ?
    	return null;
    }

	@Override
	public void setDisplayMode(DisplayMode displayMode) {
		// todo ?
		return;
	}
}
