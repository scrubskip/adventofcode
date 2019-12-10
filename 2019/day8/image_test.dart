import "package:test/test.dart";
import "package:image/image.dart";
import 'image.dart';

void main() {
  test("Layer class", _testLayerClass);
  test("Layer read", _testImageRead);
  test("Image create", _testImageWrite);
}

void _testLayerClass() {
  Layer testLayer = new Layer("123456", 3, 2);
  expect(testLayer.getValue(0, 0), 1);
  expect(testLayer.getValue(0, 1), 4);
  expect(testLayer.getValue(1, 0), 2);
  expect(testLayer.getValue(1, 1), 5);
}

void _testImageRead() {
  List<Layer> layers = parseImage("123456789012", 3, 2);
  expect(layers.length, 2);
  expect(layers[0].getValue(0, 0), 1);
  expect(layers[0].getValue(0, 1), 4);
  expect(layers[1].getValue(0, 0), 7);
  expect(layers[1].getValue(0, 1), 0);
  expect(layers[0].getZeroesCount(), 0);
  expect(layers[1].getZeroesCount(), 1);
  expect(findLeastZeroes(layers), layers[0]);
}

void _testImageWrite() {
  List<Layer> layers = parseImage("0222112222120000", 2, 2);
  Image image = getImage(layers, 2, 2);
  expect(image.getPixel(0, 0), COLOR_BLACK);
  expect(image.getPixel(1, 0), COLOR_WHITE);
  expect(image.getPixel(0, 1), COLOR_WHITE);
  expect(image.getPixel(1, 1), COLOR_BLACK);
}
