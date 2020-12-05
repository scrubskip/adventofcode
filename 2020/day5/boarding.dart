import 'dart:convert';
import "dart:io";
import "dart:math";

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();

  ArgResults argResults = parser.parse(args);
  findMissingSeatId(new File(argResults.rest[0])
          .openRead()
          .transform(utf8.decoder)
          .transform(new LineSplitter()))
      .then((result) => stdout.writeln(result));
}

Future<int> getMaxSeatId(Stream<String> input) async {
  int maxSeatId = 0;
  await for (String seat in input) {
    int seatId = getSeatId(seat);
    if (seatId >= maxSeatId) {
      maxSeatId = seatId;
    }
  }
  return maxSeatId;
}

Future<int> findMissingSeatId(Stream<String> input) async {
  int maxSeatId = null;
  int minSeatId = null;
  Set<int> seenSeats = new Set();
  await for (String seat in input) {
    int seatId = getSeatId(seat);
    if (maxSeatId == null || seatId >= maxSeatId) {
      maxSeatId = seatId;
    }
    if (minSeatId == null || seatId <= minSeatId) {
      minSeatId = seatId;
    }
    seenSeats.add(seatId);
  }
  for (int index = minSeatId + 1; index < maxSeatId - 1; index++) {
    if (seenSeats.contains(index - 1) &&
        seenSeats.contains(index + 1) &&
        !seenSeats.contains(index)) {
      return index;
    }
  }
  return -1;
}

int getSeatId(String input) {
  // First 7 characters, go front and back between 0-127
  // binary encoding
  int rowIndex = 0;
  int colIndex = 0;

  String row = input.substring(0, 7);
  // Start at the back.
  for (int index = row.length - 1; index >= 0; index--) {
    rowIndex += (row[index] == "B") ? pow(2, (row.length - 1 - index)) : 0;
  }
  String col = input.substring(7);
  for (int index = col.length - 1; index >= 0; index--) {
    colIndex += (col[index] == "R") ? pow(2, (col.length - 1 - index)) : 0;
  }

  // seat id
  return rowIndex * 8 + colIndex;
}
