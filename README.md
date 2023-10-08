# Fury Benchmarks

Fury benchmarks with:
- [x] [jackson databind](https://github.com/FasterXML/jackson-databind)
- [ ] [SBE](https://github.com/real-logic/simple-binary-encoding)

# Benchmark Setup
JMH config:
`-f 3 -wi 5 -i 5 -t 1 -w 2s -r 2s -rf csv`
OS: macos monterey
Hardware: 2.6 GHz 6-Core Intel Core i7

# Benchmark Results
## Fury vs Jackson
The benchmark results here are for reference only. Jackson is a json format, fury is a binary format, which are suitable for different scenarios.
- Fury is 41.6x faster than jackson for Struct serialization
- Fury is 65.6x faster than jackson for Struct deserialization
- Fury is 9.4x faster than jackson for MediaContent serialization
- Fury is 9.6x faster than jackson for MediaContent deserialization

```java
Benchmark                                         Mode  Cnt         Score         Error  Units
furyDeserializeMediaContent     thrpt   15  36488166.568 ± 5317389.862  ops/s
furyDeserializeStruct           thrpt   15  27231391.674 ± 3444932.318  ops/s
furySerializeMediaContent       thrpt   15  43915207.074 ± 2915185.468  ops/s
furySerializeStruct             thrpt   15  28115892.641 ± 2195125.621  ops/s
jacksonDeserializeMediaContent  thrpt   15   3791586.722 ±  185161.530  ops/s
jacksonDeserializeStruct        thrpt   15    414789.246 ±   41800.623  ops/s
jacksonSerializeMediaContent    thrpt   15   4687989.928 ±  312583.246  ops/s
jacksonSerializeStruct          thrpt   15    674588.920 ±   50615.301  ops/s
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

## Fury vs SBE
TODO
