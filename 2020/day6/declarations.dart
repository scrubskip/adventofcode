import 'dart:collection';
import 'dart:convert';
import 'dart:io';

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();

  ArgResults argResults = parser.parse(args);
  getSum(getDeclarationStream(new File(argResults.rest[0])
          .openRead()
          .transform(utf8.decoder)
          .transform(new LineSplitter())))
      .then((value) => stdout.writeln(value));
}

Stream<Set<String>> getDeclarationStream(Stream<String> source) async* {
  Set<String> group = new Set();

  await for (var chunk in source) {
    if (chunk.isEmpty) {
      // We got a new chunk, make a new group.
      yield group;
      group = new Set();
    } else {
      // otherwise, split this into pieces and add to the set.
      chunk.split("").forEach((element) {
        group.add(element);
      });
    }
  }
  if (group.isNotEmpty) {
    yield group;
  }
}

Future<int> getSum(Stream<Set<String>> declarations) async {
  return declarations
      .map((element) => element.length)
      .reduce((previous, element) => previous + element);
}

Stream<String> getListAsStream(List<String> input) async* {
  for (var value in input) {
    yield value;
  }
}
