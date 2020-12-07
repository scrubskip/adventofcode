import 'dart:collection';
import 'dart:convert';
import 'dart:io';

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();

  ArgResults argResults = parser.parse(args);
  getUnanimousSum(getDeclarationStream(new File(argResults.rest[0])
          .openRead()
          .transform(utf8.decoder)
          .transform(new LineSplitter())))
      .then((value) => stdout.writeln(value));
}

final String KEY_PEOPLE = "people";

Stream<Map<String, int>> getDeclarationStream(Stream<String> source) async* {
  Map<String, int> group = new Map();
  group[KEY_PEOPLE] = 0;
  await for (var chunk in source) {
    if (chunk.isEmpty) {
      // We got a new chunk, make a new group.
      yield group;
      group = new Map();
      group[KEY_PEOPLE] = 0;
    } else {
      group[KEY_PEOPLE]++;
      // otherwise, split this into pieces and add to the set.
      chunk.split("").forEach((element) {
        group.putIfAbsent(element, () => 0);
        group[element]++;
      });
    }
  }
  if (group[KEY_PEOPLE] > 0) {
    yield group;
  }
}

Future<int> getSum(Stream<Map<String, int>> declarations) async {
  return declarations
      .map((element) => element.length - 1) // remove KEY_PEOPLE
      .reduce((previous, element) => previous + element);
}

Future<int> getUnanimousSum(Stream<Map<String, int>> declarations) async {
  return declarations
      .map((element) =>
          element.entries
              .where((question) => question.value == element[KEY_PEOPLE])
              .length -
          1) // remove KEY_PEOPLE
      .reduce((previous, element) => previous + element);
}

Stream<String> getListAsStream(List<String> input) async* {
  for (var value in input) {
    yield value;
  }
}
