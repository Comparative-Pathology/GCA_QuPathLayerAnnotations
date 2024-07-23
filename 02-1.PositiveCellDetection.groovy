// (C) University of Edinburgh, Edinburgh, UK
// (C) Heriot-Watt University, Edinburgh, UK
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// This program is distributed in the hope that it will be
// useful but WITHOUT ANY WARRANTY; without even the implied
// warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
// PURPOSE.  See the GNU General Public License for more
// details.
///You should have received a copy of the GNU General Public
// License along with this program; if not, write to the Free
// Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
// Boston, MA  02110-1301, USA.

import static qupath.lib.gui.scripting.QPEx.*

// Set image type and specific stains values - these need adjusting based on each stain
setImageType('BRIGHTFIELD_H_DAB');
setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.76794 0.58716 0.25595 ", "Stain 2" : "DAB", "Values 2" : "0.34966 0.49606 0.79477 ", "Background" : " 196 201 218 "}');

// Detect annotations and set specific options for "Positive Cell Detection" function in QuPath.
println getAnnotationObjects().size() + " annotations found"
selectAnnotations();
runPlugin('qupath.imagej.detect.cells.PositiveCellDetection', '{"detectionImageBrightfield": "Optical density sum",  "requestedPixelSizeMicrons": 0.7,  "backgroundRadiusMicrons": 10.0,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 1.0,  "minAreaMicrons": 10.0,  "maxAreaMicrons": 400.0,  "threshold": 0.1,  "maxBackground": 2.0,  "watershedPostProcess": true,  "excludeDAB": false,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true,  "thresholdCompartment": "Nucleus: DAB OD mean",  "thresholdPositive1": 0.1574,  "thresholdPositive2": 0.4,  "thresholdPositive3": 0.6000000000000001,  "singleThreshold": true}');

// Export the results - Counts
def name_count = GeneralTools.getNameWithoutExtension(getProjectEntry().getImageName())  + '.csv'
def path = buildFilePath(PROJECT_BASE_DIR, 'positive_cell_detection_annotations')
mkdirs(path)
path = buildFilePath(path, name_count)
saveAnnotationMeasurements(path)
print 'Results exported to ' + path
