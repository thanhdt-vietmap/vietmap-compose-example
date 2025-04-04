// This file is generated.
// Edit platform/darwin/scripts/generate-style-code.js, then run `make darwin-style-code`.

#import "MLNFoundation.h"
#import "MLNVectorStyleLayer.h"

NS_ASSUME_NONNULL_BEGIN

/**
 An `MLNHeatmapStyleLayer` is a style layer that renders a <a
 href="https://en.wikipedia.org/wiki/Heat_map">heatmap</a>.
 
 A heatmap visualizes the spatial distribution of a large, dense set of point
 data, using color to avoid cluttering the map with individual points at low
 zoom levels. The points are weighted by an attribute you specify. Use a heatmap
 style layer in conjunction with point or point collection features. These
 features can come from vector tiles loaded by an `MLNVectorTileSource` object,
 or they can be `MLNPointAnnotation`, `MLNPointFeature`, `MLNPointCollection`,
 or `MLNPointCollectionFeature` instances in an `MLNShapeSource` or
 `MLNComputedShapeSource` object.
 
 Consider accompanying a heatmap style layer with an `MLNCircleStyleLayer` or
 `MLNSymbolStyleLayer` at high zoom levels. If you are unsure whether the point
 data in an `MLNShapeSource` is dense enough to warrant a heatmap, you can
 alternatively cluster the source using the `MLNShapeSourceOptionClustered`
 option and render the data using an `MLNCircleStyleLayer` or
 `MLNSymbolStyleLayer`.

 You can access an existing heatmap style layer using the
 `-[MLNStyle layerWithIdentifier:]` method if you know its identifier;
 otherwise, find it using the `MLNStyle.layers` property. You can also create a
 new heatmap style layer and add it to the style using a method such as
 `-[MLNStyle addLayer:]`.

 #### Related examples
 See the <a
 href="https://docs.mapbox.com/ios/maps/examples/heatmap-example/">Create a
 heatmap layer</a> example to learn how to add this style layer to your map.

 ### Example

 ```swift
 ```
 */
MLN_EXPORT
@interface MLNHeatmapStyleLayer : MLNVectorStyleLayer

/**
 Returns a heatmap style layer initialized with an identifier and source.

 After initializing and configuring the style layer, add it to a map view’s
 style using the `-[MLNStyle addLayer:]` or
 `-[MLNStyle insertLayer:belowLayer:]` method.

 @param identifier A string that uniquely identifies the source in the style to
    which it is added.
 @param source The source from which to obtain the data to style. If the source
    has not yet been added to the current style, the behavior is undefined.
 @return An initialized foreground style layer.
 */
- (instancetype)initWithIdentifier:(NSString *)identifier source:(MLNSource *)source;

// MARK: - Accessing the Paint Attributes

#if TARGET_OS_IPHONE
/**
 The color of each screen point based on its density value in a heatmap. This
 property is normally set to an interpolation or step expression with the
 `$heatmapDensity` value as its input.
 
 The default value of this property is an expression that evaluates to a rainbow
 color scale from blue to red. Set this property to `nil` to reset it to the
 default value.
 
 You can set this property to an expression containing any of the following:
 
 * Constant `UIColor` values
 * Predefined functions, including mathematical and string operators
 * Conditional expressions
 * Variable assignments and references to assigned variables
 * Interpolation and step functions applied to the `$heatmapDensity` variable
 
 This property does not support applying interpolation or step functions to
 feature attributes.
 */
@property (nonatomic, null_resettable) NSExpression *heatmapColor;
#else
/**
 The color of each screen point based on its density value in a heatmap. This
 property is normally set to an interpolation or step expression with the
 `$heatmapDensity` value as its input.
 
 The default value of this property is an expression that evaluates to a rainbow
 color scale from blue to red. Set this property to `nil` to reset it to the
 default value.
 
 You can set this property to an expression containing any of the following:
 
 * Constant `NSColor` values
 * Predefined functions, including mathematical and string operators
 * Conditional expressions
 * Variable assignments and references to assigned variables
 * Interpolation and step functions applied to the `$heatmapDensity` variable
 
 This property does not support applying interpolation or step functions to
 feature attributes.
 */
@property (nonatomic, null_resettable) NSExpression *heatmapColor;
#endif

/**
 Similar to `heatmapWeight` but controls the intensity of the heatmap globally.
 Primarily used for adjusting the heatmap based on zoom level.
 
 The default value of this property is an expression that evaluates to the float
 `1`. Set this property to `nil` to reset it to the default value.
 
 You can set this property to an expression containing any of the following:
 
 * Constant numeric values no less than 0
 * Predefined functions, including mathematical and string operators
 * Conditional expressions
 * Variable assignments and references to assigned variables
 * Interpolation and step functions applied to the `$zoomLevel` variable
 
 This property does not support applying interpolation or step functions to
 feature attributes.
 */
@property (nonatomic, null_resettable) NSExpression *heatmapIntensity;

/**
 The transition affecting any changes to this layer’s `heatmapIntensity` property.

 This property corresponds to the `heatmap-intensity-transition` property in the style JSON file format.
*/
@property (nonatomic) MLNTransition heatmapIntensityTransition;

/**
 The global opacity at which the heatmap layer will be drawn.
 
 The default value of this property is an expression that evaluates to the float
 `1`. Set this property to `nil` to reset it to the default value.
 
 You can set this property to an expression containing any of the following:
 
 * Constant numeric values between 0 and 1 inclusive
 * Predefined functions, including mathematical and string operators
 * Conditional expressions
 * Variable assignments and references to assigned variables
 * Interpolation and step functions applied to the `$zoomLevel` variable
 
 This property does not support applying interpolation or step functions to
 feature attributes.
 */
@property (nonatomic, null_resettable) NSExpression *heatmapOpacity;

/**
 The transition affecting any changes to this layer’s `heatmapOpacity` property.

 This property corresponds to the `heatmap-opacity-transition` property in the style JSON file format.
*/
@property (nonatomic) MLNTransition heatmapOpacityTransition;

/**
 Radius of influence of one heatmap point in points. Increasing the value makes
 the heatmap smoother, but less detailed.
 
 This property is measured in points.
 
 The default value of this property is an expression that evaluates to the float
 `30`. Set this property to `nil` to reset it to the default value.
 
 You can set this property to an expression containing any of the following:
 
 * Constant numeric values no less than 1
 * Predefined functions, including mathematical and string operators
 * Conditional expressions
 * Variable assignments and references to assigned variables
 * Interpolation and step functions applied to the `$zoomLevel` variable and/or
 feature attributes
 */
@property (nonatomic, null_resettable) NSExpression *heatmapRadius;

/**
 The transition affecting any changes to this layer’s `heatmapRadius` property.

 This property corresponds to the `heatmap-radius-transition` property in the style JSON file format.
*/
@property (nonatomic) MLNTransition heatmapRadiusTransition;

/**
 A measure of how much an individual point contributes to the heatmap. A value
 of 10 would be equivalent to having 10 points of weight 1 in the same spot.
 Especially useful when combined with clustering.
 
 The default value of this property is an expression that evaluates to the float
 `1`. Set this property to `nil` to reset it to the default value.
 
 You can set this property to an expression containing any of the following:
 
 * Constant numeric values no less than 0
 * Predefined functions, including mathematical and string operators
 * Conditional expressions
 * Variable assignments and references to assigned variables
 * Interpolation and step functions applied to the `$zoomLevel` variable and/or
 feature attributes
 */
@property (nonatomic, null_resettable) NSExpression *heatmapWeight;

@end

NS_ASSUME_NONNULL_END
