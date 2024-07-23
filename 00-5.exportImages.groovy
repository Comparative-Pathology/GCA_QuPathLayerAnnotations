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


import qupath.imagej.tools.IJTools
import qupath.lib.gui.images.servers.RenderedImageServer
import qupath.lib.gui.viewer.overlays.HierarchyOverlay
import qupath.lib.regions.RegionRequest
import static qupath.lib.gui.scripting.QPEx.*


// ======================================================================= //
// ORIGINAL IMAGE
def imageData = getCurrentImageData()
// Generate folder "original" inside the project folder
def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
def pathOutput = buildFilePath(PROJECT_BASE_DIR, 'original')
mkdirs(pathOutput)

def server = getCurrentServer()
// Generate a filepath for the image
pathOriginal = buildFilePath(pathOutput, name+"_original.tif")
// Write the full image downsampled by a factor of 10
def requestFull = RegionRequest.createInstance(server, 10)
writeImageRegion(server, requestFull, pathOriginal)
print 'DONE! Original image exported to ' + pathOriginal


// ======================================================================= //
// ANNOTATED IMAGE

// It is important to define the downsample!
// This is required to determine annotation line thicknesses
double downsample = 10

// Add the output file path here
String path = buildFilePath(PROJECT_BASE_DIR, 'annotated', getProjectEntry().getImageName() + '_annotated.tif')

// Request the current viewer for settings, and current image (which may be used in batch processing)
def viewer = getCurrentViewer()
def imageData_annot = getCurrentImageData()

// Create a rendered server that includes a hierarchy overlay using the current display settings
def server_annot = new RenderedImageServer.Builder(imageData_annot)
    .downsamples(downsample)
    .layers(new HierarchyOverlay(viewer.getImageRegionStore(), viewer.getOverlayOptions(), imageData_annot))
    .build()
	
mkdirs(new File(path).getParent())
writeImage(server_annot, path)
print 'DONE! Annotated image exported to ' + path


// ======================================================================= //
// CLASSIFIED IMAGE
etImageType('BRIGHTFIELD_OTHER');
// Sets base values for each stain for all images
setColorDeconvolutionStains('{"Name" : "PSR", "Stain 1" : "Hematoxylin", "Values 1" : "0.534 0.675 0.509 ", "Stain 2" : "PSR", "Values 2" : "0.159 0.837 0.524 ", "Background" : " 226 220 232 "}');

// Selects annotations
println getAnnotationObjects().size() + " annotations found"
selectAnnotations();

// Loads the pixel classifier - change the name here from "Collagen_Ileum"
addPixelClassifierMeasurements("Collagen_Ileum", "Collagen_Ileum")

// Save the classified image
def name_class = server.getMetadata().getName()
// image file name + _classified.tif
def name_short = GeneralTools.getNameWithoutExtension(getProjectEntry().getImageName()) +  "_classified.tif"
// Location where the classified images will be saved within the project folder
def classified_path = buildFilePath(PROJECT_BASE_DIR, 'classified_images')
mkdirs(classified_path)
pathClassified = buildFilePath(classified_path, name_short)
// 'Collagen_Ileum' - name of the classifier - change it for different classifier
writePredictionImage('Collagen_Ileum', pathClassified)
print 'DONE! Classified image exported to ' + pathClassified

// Export the results - Counts
def name_count = GeneralTools.getNameWithoutExtension(getProjectEntry().getImageName())  + '.csv'
def path_count = buildFilePath(PROJECT_BASE_DIR, 'annotations_quantified')
mkdirs(path_count)
path_count = buildFilePath(path_count, name_count)
saveAnnotationMeasurements(path_count)
print 'Quantification results exported to ' + path_count
