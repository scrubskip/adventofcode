<<<<<<< HEAD
import 'dart:io';

void main(List<String> arguments) {
  exitCode = 0;
  if (arguments.length == 1) {
    var path = arguments[0];
  }
=======
import "dart:async";
import 'dart:convert';
import "dart:io";

import "package:args/args.dart";

const justOnce = 'just-once';

ArgResults _argResults;

void main(List<String> args) {
  exitCode = 0; //presume success
  final parser = new ArgParser()
    ..addFlag(justOnce, negatable: false, abbr: 'n');

  _argResults = parser.parse(args);
  new File(_argResults.rest[0])
      .openRead()
      .transform(utf8.decoder)
      .transform(const LineSplitter())
      .transform(StreamTransformer.fromHandlers(
          handleData: (String event, EventSink output) {
        output.add(int.parse(event));
      }))
      .toList()
      .then((list) => analyzeFrequency(list, justOnce: _argResults[justOnce]));
}

analyzeFrequency(List frequencies, {bool justOnce = true}) {
  var currentFrequency = 0;
  Set seenFrequencies = new Set();
  var firstSeen = null;
  while (firstSeen == null) {
    for (var frequency in frequencies) {
      // parse as number
      currentFrequency += frequency;
      if (firstSeen == null && seenFrequencies.contains(currentFrequency)) {
        firstSeen = currentFrequency;
      }
      seenFrequencies.add(currentFrequency);
    }
    if (justOnce) {
      break;
    }
  }

  stdout.writeln(currentFrequency);
  stdout.writeln(firstSeen);
>>>>>>> 78c388cd83ba6f287929463c4cc80308b70ab9d1
}
