import static qupath.lib.gui.scripting.QPEx.*

setImageType('BRIGHTFIELD_OTHER');
setColorDeconvolutionStains('{"Name" : "PSR", "Stain 1" : "Hematoxylin", "Values 1" : "0.55854 0.71242 0.42484", "Stain 2" : "PSR", "Values 2" : "0.12201 0.85206 0.50903", "Background" : " 226 220 231"}');

println getAnnotationObjects().size() + " annotations found"
selectAnnotations();
addPixelClassifierMeasurements("PSR_triple_high", "PSR_triple_high")


// Save the classified image
def server = getCurrentServer()
def name = server.getMetadata().getName()
def name_short = GeneralTools.stripExtension(getProjectEntry().getImageName()) +  "_classified.png"
def classified_path = buildFilePath(PROJECT_BASE_DIR, 'classified_images')
mkdirs(classified_path)
pathClassified = buildFilePath(classified_path, name_short)
writePredictionImage('PSR_triple_high', pathClassified)
print 'Image exported to ' + pathClassified


// Export the results - Counts
def name_count = GeneralTools.getNameWithoutExtension(getProjectEntry().getImageName())  + '.csv'
def path = buildFilePath(PROJECT_BASE_DIR, 'annotations')
mkdirs(path)
path = buildFilePath(path, name_count)
saveAnnotationMeasurements(path)
print 'Results exported to ' + path
