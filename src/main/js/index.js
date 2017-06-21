var $ = require("jquery");
var ol = require('openlayers');

function init() {

  $.get('/api/config', function(config) {
    $.get('/api/tracks', function(tracks) {
      display(config, tracks);
    });
  });
}

function display(config, tracks) {

  var raster = new ol.layer.Tile({
    source: new ol.source.BingMaps({
      imagerySet: 'Road',
      key: config.bingKey
    })
  });;

  var style = {
    'Point': new ol.style.Style({
      image: new ol.style.Circle({
        fill: new ol.style.Fill({
          color: 'rgba(255,255,0,0.4)'
        }),
        radius: 5,
        stroke: new ol.style.Stroke({
          color: '#ff0',
          width: 1
        })
      })
    }),
    'LineString': new ol.style.Style({
      stroke: new ol.style.Stroke({
        color: '#f00',
        width: 3
      })
    }),
    'MultiLineString': new ol.style.Style({
      stroke: new ol.style.Stroke({
        color: '#ff0000',
        width: 3
      })
    })
  };

  var layers = tracks.map(function(track) {
    
    return new ol.layer.Vector({
      source: new ol.source.Vector({
        url: '/api/track/' + track,
        format: new ol.format.GPX()
      }),
      style: function(feature) {
        return style[feature.getGeometry().getType()];
      }
    });
    
  });
    
  var map = new ol.Map({
    target: 'map',
    layers: [raster].concat(layers),
    view: new ol.View({
      center: [0, 0],
      zoom: 2
    })
  });

  map.getView().fit([
    864855.8589097988,
    5095952.99504686,
    3389718.3346956447,
    7691194.912666457
  ]);

  // a normal select interaction to handle click
  var select = new ol.interaction.Select();
  map.addInteraction(select);

  var selectedFeatures = select.getFeatures();

  // a DragBox interaction used to select features by drawing boxes
  var dragBox = new ol.interaction.DragBox({
    condition: ol.events.condition.platformModifierKeyOnly
  });

  map.addInteraction(dragBox);

  dragBox.on('boxend', function() {
    // features that intersect the box are added to the collection of
    // selected features
    var extent = dragBox.getGeometry().getExtent();
    
    for(let layer of layers) {
      layer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
        selectedFeatures.push(feature);
      });
      if(selectedFeatures.getLength() > 0) break;
    }
  });

  // clear selection when drawing a new box and when clicking on the map
  dragBox.on('boxstart', function() {
    selectedFeatures.clear();
  });

  selectedFeatures.on(['add', 'remove'], function() {
    var names = selectedFeatures.getArray().map(function(feature) {
      return feature.get('name');
    });
    console.log('Name: ' + name);
  });
 
}


init();
