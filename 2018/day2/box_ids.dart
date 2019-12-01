import "dart:async";
import 'dart:convert';
import 'dart:collection';
import "dart:io";

import "package:args/args.dart";

const operation = 'op';

ArgResults _argResults;

void main(List<String> args) {
  exitCode = 0;

  final parser = ArgParser()..addOption(operation);

  _argResults = parser.parse(args);

  Future<List<String>> lineList = new File(_argResults.rest[0])
      .openRead()
      .transform(utf8.decoder)
      .transform(const LineSplitter())
      .toList();

  if ("checksum" == _argResults[operation]) {
    lineList.then((list) => stdout.writeln(getChecksum(list)));
  } else if ("findcommon" == _argResults[operation]) {
    lineList.then((list) => stdout.writeln(findCommon(list)));
  } else {
    exitCode = 1;
    stdout.writeln("Unknown operation " + operation);
  }
}

int getChecksum(List<String> boxIds) {
  int twoCount = 0;
  int threeCount = 0;
  for (String boxId in boxIds) {
    Map<int, int> freqCount = getFrequencyCount(boxId);
    if (freqCount.containsValue(2)) {
      twoCount++;
    }
    if (freqCount.containsValue(3)) {
      threeCount++;
    }
  }
  return twoCount * threeCount;
}

Map<int, int> getFrequencyCount(String boxId) {
  Map<int, int> freqCount = new HashMap<int, int>();
  for (int codeUnit in boxId.codeUnits) {
    freqCount.putIfAbsent(codeUnit, () => 0);
    freqCount[codeUnit]++;
  }

  return freqCount;
}

String findCommon(List<String> boxIds) {
  for (String boxId in boxIds) {
    for (String otherId in boxIds) {
      String commonString = getCommonString(boxId, otherId);
      if (commonString.length == boxId.length - 1) {
        return commonString;
      }
    }
  }
  return "";
}

String getCommonString(String boxId, String otherId) {
  if (boxId.length != otherId.length) {
    return "";
  }
  StringBuffer buffer = new StringBuffer();
  for (int index = 0; index < boxId.length; index++) {
    if (boxId.codeUnitAt(index) == otherId.codeUnitAt(index)) {
      buffer.writeCharCode(boxId.codeUnitAt(index));
    }
  }
  return buffer.toString();
}
