import 'dart:async';
import 'dart:collection';
import 'dart:convert';
import "dart:io";

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();

  ArgResults argResults = parser.parse(args);
  getPassportStream(new File(argResults.rest[0])
          .openRead()
          .transform(utf8.decoder)
          .transform(new LineSplitter()))
      .where(isValid)
      .toList()
      .then((List<Map<String, String>> results) =>
          stdout.writeln(results.length));
}

Stream<String> getListAsStream(List<String> input) async* {
  for (var value in input) {
    yield value;
  }
}

Stream<Map<String, String>> getPassportStream(Stream<String> source) async* {
  Map<String, String> passport = new HashMap();

  await for (var chunk in source) {
    if (chunk.isEmpty) {
      // We got a new chunk, make a new passport.
      yield passport;
      passport = new HashMap();
    } else {
      // otherwise, split this into pieces and add to the hashmap.
      chunk.split(" ").forEach((element) {
        List<String> atoms = element.split(":");
        passport[atoms[0]] = atoms[1];
      });
    }
  }
  if (passport.isNotEmpty) {
    yield passport;
  }
}

final List<String> REQUIRED_FIELDS = [
  "byr",
  "iyr",
  "ecl",
  "eyr",
  "hcl",
  "hgt",
  "pid"
];

bool isValid(Map<String, String> passport) {
  return REQUIRED_FIELDS
          .where((element) => passport.containsKey(element))
          .length ==
      REQUIRED_FIELDS.length;
}
