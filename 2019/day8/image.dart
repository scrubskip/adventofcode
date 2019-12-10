import "dart:io";

import "package:args/args.dart";
import "package:image/image.dart";

ArgResults _argResults;

final int COLOR_BLACK = Color.fromRgb(0, 0, 0);
final int COLOR_WHITE = Color.fromRgb(255, 255, 255);

void main(List<String> args) async {
  final parser = ArgParser();

  _argResults = parser.parse(args);
  String input = await new File(_argResults.rest[0]).readAsString();
  int width = 25;
  int height = 6;
  List<Layer> layers = parseImage(input, width, height);
  // Find the layer with the least 0s.
  Layer layer = findLeastZeroes(layers);
  if (layer != null) {
    int onesCount = layer.getDigitCount("1");
    int twosCount = layer.getDigitCount("2");
    int result = onesCount * twosCount;
    stdout.writeln("Output: $result");
  }
  Image image = getImage(layers, width, height);
  File outputFile = new File(_argResults.rest[1]);
  PngEncoder encoder = new PngEncoder();
  outputFile.writeAsBytesSync(encoder.encodeImage(image));
}

List<Layer> parseImage(String input, int width, int height) {
  int readPosition = 0;
  int layerLength = width * height;
  List<Layer> outputLayers = [];
  while (readPosition < input.length - 1) {
    outputLayers.add(new Layer(
        input.substring(readPosition, readPosition + layerLength),
        width,
        height));
    readPosition += layerLength;
  }
  return outputLayers;
}

Layer findLeastZeroes(List<Layer> layers) {
  if (layers.length == 0) {
    return null;
  }
  int leastZeroes = layers[0].getZeroesCount();
  Layer leastLayer = layers[0];
  if (layers.length > 1) {
    layers.skip(1).forEach((layer) => {
          if (layer.getZeroesCount() < leastZeroes)
            {leastZeroes = layer.getZeroesCount(), leastLayer = layer}
        });
  }
  return leastLayer;
}

Image getImage(List<Layer> layers, int width, int height) {
  Image image = new Image(width, height);
  // Loop through pixels. For each layer, if it's 2, go down the layers until we see a 0 or 1.
  for (int x = 0; x < width; x++) {
    for (int y = 0; y < height; y++) {
      for (Layer layer in layers) {
        int value = layer.getValue(x, y);
        if (value != 2) {
          image.setPixel(x, y, value == 0 ? COLOR_BLACK : COLOR_WHITE);
          break;
        }
      }
    }
  }
  return image;
}

class Layer {
  String _rawValue;
  int width;
  int height;

  Layer(String rawValue, this.width, this.height) {
    this._rawValue = rawValue;
    assert(this._rawValue.length == this.width * this.height);
  }

  int getValue(int x, int y) {
    return int.parse(this._rawValue[x + (y * this.width)]);
  }

  int getDigitCount(String digit) {
    int count = 0;
    for (int i = 0; i < this._rawValue.length; i++) {
      if (this._rawValue[i] == digit) {
        count++;
      }
    }
    return count;
  }

  int getZeroesCount() {
    return getDigitCount("0");
  }
}
