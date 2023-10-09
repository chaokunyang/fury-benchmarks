# Fury Benchmarks

Fury benchmarks with:
- [x] [jackson databind](https://github.com/FasterXML/jackson-databind)
- [x] [Microstream](https://github.com/real-logic/simple-binary-encoding)

# Benchmark Setup
JMH config:
`-f 3 -wi 5 -i 5 -t 1 -w 2s -r 2s -rf csv`
OS: macos monterey
Hardware: 2.6 GHz 6-Core Intel Core i7

# Benchmark Results
## Fury vs Jackson
The benchmark results here are for reference only. Jackson is a json format, fury is a binary format, which are suitable for different scenarios.
- Fury is 32.1x faster than jackson for Struct serialization
- Fury is 45x faster than jackson for Struct deserialization
- Fury is 8.8x faster than jackson for MediaContent serialization
- Fury is 11.8x faster than jackson for MediaContent deserialization

```java
Benchmark                                         Mode  Cnt         Score         Error  Units
JacksonBenchmark.furyDeserializeMediaContent     thrpt   15   2783918.470 ±   68075.351  ops/s
JacksonBenchmark.furyDeserializeStruct           thrpt   15  13686679.435 ± 1440816.885  ops/s
JacksonBenchmark.furySerializeMediaContent       thrpt   15   3410893.820 ±  265913.345  ops/s
JacksonBenchmark.furySerializeStruct             thrpt   15  17004519.876 ±  463107.804  ops/s
JacksonBenchmark.jacksonDeserializeMediaContent  thrpt   15    235938.122 ±   23054.241  ops/s
JacksonBenchmark.jacksonDeserializeStruct        thrpt   15    302600.964 ±   19586.158  ops/s
JacksonBenchmark.jacksonSerializeMediaContent    thrpt   15    386182.017 ±   38637.729  ops/s
JacksonBenchmark.jacksonSerializeStruct          thrpt   15    529044.347 ±   31945.826  ops/s
```
Compute speedup:
```python
import pandas as pd
df = pd.read_csv("jmh-result.csv")
s = df["Score"]
s[:4].to_numpy()/s[4:].to_numpy()
```
## Fury vs Microstream
Fury is 5x smaller for serialized binary size at most:
```java
furyMediaContentBytes size 336
furyStructBytes size 90
microstream mediaContentBytes size 1527
microstream structBytes size 120
```
Fury is :
```java
Benchmark                                                 Mode  Cnt         Score          Error  Units
MicrostreamBenchmark.furyDeserializeMediaContent         thrpt    3   2082751.515 ±  2638613.695  ops/s
MicrostreamBenchmark.furyDeserializeStruct               thrpt    3  10334292.992 ± 10542478.626  ops/s
MicrostreamBenchmark.furySerializeMediaContent           thrpt    3   2898943.216 ±   946978.784  ops/s
MicrostreamBenchmark.furySerializeStruct                 thrpt    3  13980885.316 ± 13424528.901  ops/s
MicrostreamBenchmark.microstreamDeserializeMediaContent  thrpt    3     76354.367 ±   231329.651  ops/s
MicrostreamBenchmark.microstreamSerializeMediaContent    thrpt    3     66316.198 ±   729572.587  ops/s
MicrostreamBenchmark.microstreamSerializeStruct          thrpt    3    233374.717 ±   732378.317  ops/s
```
