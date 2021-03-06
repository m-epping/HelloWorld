/**
 * m-epping/HelloWorld - A repository for testing purposes
 * 
 * Copyright (c) 2013, Michael Epping
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package draft_drift;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.YesNoCancelDialog;
import ij.plugin.filter.ExtendedPlugInFilter;
import ij.plugin.filter.PlugInFilterRunner;
import ij.process.ImageProcessor;

/**
 * This ImageJ plug-in is part of EFTEMj. It implements the first step of a drift correction that is applied to stacks.
 * The drift correction is divided into a drift detection (this plug-in) and an image shifter for stacks (
 * {@link StackTranslation}). DriftDetection provides an automatic and a manual detection method. Several
 * {@link GenericDialog}s are used to select the method and setup the automatic detection. The automatic detection
 * calculates normalised cross-correlation coefficients to determine the drift between two images of a stack.
 * 
 * @author Michael Epping <michael.epping@uni-muenster.de>
 * 
 */
public class DriftDetection implements ExtendedPlugInFilter {

    private static final int AUTOMATIC = 1;
    private static final int MANUAL = 2;
    private static final int CANCEL = 0;
    private int flags = DOES_32 | NO_CHANGES | FINAL_PROCESSING;

    /*
     * (non-Javadoc)
     * 
     * @see ij.plugin.filter.PlugInFilter#setup(java.lang.String, ij.ImagePlus)
     */
    @Override
    public int setup(String arg, ImagePlus imp) {
	if (arg == "final") {
	    // TODO implement final processing
	    return DONE;
	}
	return flags;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ij.plugin.filter.PlugInFilter#run(ij.process.ImageProcessor)
     */
    @Override
    public void run(ImageProcessor ip) {
	// TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see ij.plugin.filter.ExtendedPlugInFilter#showDialog(ij.ImagePlus, java.lang.String,
     * ij.plugin.filter.PlugInFilterRunner)
     */
    @Override
    public int showDialog(ImagePlus imp, String command, PlugInFilterRunner pfr) {
	if (imp.getStackSize() <= 1) {
	    // TODO implement stack creation
	    return DONE;
	}
	switch (showModeDialog(command)) {
	case AUTOMATIC:
	    IJ.showMessage("Automatic drift detection has been selected.");
	    // TODO implement automatic drift detection
	    break;
	case MANUAL:
	    IJ.showMessage("Manual drift detection has been selected.");
	    // TODO implement manual drift detection
	    break;
	default:
	    IJ.showMessage("Drift detection has been canceled.");
	    return NO_CHANGES | DONE;
	}
	return flags;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ij.plugin.filter.ExtendedPlugInFilter#setNPasses(int)
     */
    @Override
    public void setNPasses(int nPasses) {
	// This method is not used.
    }

    /**
     * The user is asked to select the drift detection mode. A {@link YesNoCancelDialog} is used for this purpose. If
     * Yes is selected the automatic mode is used. No means the manual mode is used.
     * 
     * @param title
     * @return the selected mode for drift detection
     */
    private int showModeDialog(String title) {
	YesNoCancelDialog dialog = new YesNoCancelDialog(IJ.getInstance(), title,
		"Use automatic drift detection?\nTo start manual mode select no.");
	if (dialog.yesPressed()) {
	    return AUTOMATIC;
	} else if (dialog.cancelPressed()) {
	    return CANCEL;
	}
	return MANUAL;
    }
}
