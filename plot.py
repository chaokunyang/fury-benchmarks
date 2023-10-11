# Copyright 2023 The Fury Authors
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""
    process fury/kryo/fst/hession performance data
"""
import datetime
from collections import UserDict

import matplotlib.pyplot as plt
import os
import pandas as pd
import re
import sys

dir_path = os.path.dirname(os.path.realpath(__file__))


color_map = {
    "Fury": "#7845FD",
    "Furymetashared": "#B237ED",  # (1, 0.65, 0.55)
    # "Kryo": (1, 0.5, 1),
    # "Kryo": (1, 0.84, 0.25),
    "Kryo": "#55BCC2",
    "Kryo_deserialize": "#55BCC2",
    "Fst": (0.90, 0.43, 0.5),
    "Hession": (0.80, 0.5, 0.6),
    "Hession_deserialize": (0.80, 0.5, 0.6),
    "Protostuff": (1, 0.84, 0.66),
    "Jdk": (0.55, 0.40, 0.45),
}


class ColorMap(UserDict):

    def __getitem__(self, item: str):
        value = color_map.get(item)
        if value is None:
            if item.startswith("fury"):
                return color_map["Fury"]
            if item.startswith("kryo"):
                return color_map["Kryo"]
        return value


scaler = 10000


def format_scaler(x):
    if x > 100:
        return round(x)
    else:
        return round(x, 1)


def plot(df: pd.DataFrame, file_dir, filename, column="Tps"):
    df["Tps"] = df["Score"]
    df["ns"] = (1 / df["Tps"] * 10**9).astype(int)
    benchmark = df["Benchmark"].apply(lambda x: camel_to_snake(x.rsplit(".", 1)[-1]))
    df[["Lib", "Benchmark", "DataType"]] = benchmark.str.split(
        pat="_", n=2, expand=True
    )
    df["Lib"] = df["Lib"].str.capitalize()
    group_cols = ["DataType"]
    plot_color_map = ColorMap()
    ylable = column
    if column == "Tps" and scaler != 1:
        ylable = f"Tps/{scaler}"
        df[column] = (df[column] / scaler).apply(format_scaler)
    grouped = df.groupby(group_cols)
    files_dict = {}
    count = 0
    for keys, sub_df in grouped:
        count = count + 1
        sub_df = sub_df[["Lib", "Benchmark", column]]
        title = keys
        kind = "Time" if column == "ns" else "Tps"
        save_filename = f"""{filename}_{title.replace(" ", "_")}_{kind.lower()}"""
        cnt = files_dict.get(save_filename, 0)
        if cnt > 0:
            files_dict[save_filename] = cnt = cnt + 1
            save_filename += "_" + cnt
        title = f"{title} ({kind})"
        fig, ax = plt.subplots()
        final_df = (
            sub_df.reset_index(drop=True)
            .set_index(["Lib", "Benchmark"])
            .unstack("Lib")
        )
        print(final_df)
        libs = final_df.columns.to_frame()["Lib"]
        color = [plot_color_map[lib] for lib in libs]
        sub_plot = final_df.plot.bar(
            title=title, color=color, ax=ax, figsize=(7, 7), width=0.7, rot=0
        )
        for container in ax.containers:
            ax.bar_label(container)
        ax.set_xlabel("serialize/deserialize")
        ax.set_ylabel(ylable)
        ax.legend(libs, loc="upper right", prop={"size": 13})
        save_dir = get_plot_dir(file_dir)
        sub_plot.get_figure().savefig(save_dir + "/" + save_filename)


time_str = datetime.datetime.now().strftime("%m%d_%H%M_%S")


def get_plot_dir(_file_dir):
    plot_dir = _file_dir + "/" + time_str
    if not os.path.exists(plot_dir):
        os.makedirs(plot_dir)
    return plot_dir


def camel_to_snake(name):
    name = re.sub("(.)([A-Z][a-z]+)", r"\1_\2", name)
    return re.sub("([a-z\\d])([A-Z])", r"\1_\2", name).lower()


if __name__ == "__main__":
    # size_markdown = get_datasize_markdown("""
    # """)
    # print(size_markdown)
    args = sys.argv[1:]
    if args:
        file_name = args[0]
    else:
        file_name = "jmh-result.csv"
    file_dir = dir_path
    plot(pd.read_csv(f"{file_dir}/{file_name}"), file_dir, "bench", column="Tps")
