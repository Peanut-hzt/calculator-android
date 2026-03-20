# 科学计算器 / Scientific Calculator

[English](#english) | [中文](#中文)

---

## English

### Overview

An Android calculator app with two main functions:
1. **Scientific Calculator** - Advanced mathematical operations
2. **Unit Converter** - Common unit conversions

### Features

#### Scientific Calculator
- **Basic Operations**: Addition (+), Subtraction (-), Multiplication (×), Division (÷)
- **Percentage**: Calculate percentages (%)
- **Power Operations**: Power (xʸ), Square (x²)
- **Trigonometric Functions**: sin, cos, tan (degree mode)
- **Logarithmic Functions**: log (base 10), ln (natural log)
- **Square Root**: √
- **Mathematical Constants**: π (Pi), e (Euler's number)

#### Unit Converter
- **Length**: mm, cm, m, km, in, ft, yd, mi
- **Area**: mm², cm², m², km², in², ft², yd², acre, ha
- **Weight**: mg, g, kg, t, oz, lb, st
- **Temperature**: Celsius (°C), Fahrenheit (°F), Kelvin (K)
- **Currency**: CNY, USD, EUR, JPY, GBP, HKD, KRW

### How to Use

1. Download and install the APK on your Android device
2. Tap the tabs to switch between:
   - **科学计算器** (Scientific Calculator)
   - **换算计算器** (Unit Converter)

#### Scientific Calculator Usage

| Button | Function | Example |
|--------|----------|---------|
| AC | Clear all | Reset calculator |
| ⌫ | Delete last digit | Remove last input |
| sin/cos/tan | Trigonometric functions | sin 30 = 0.5 |
| log/ln | Logarithmic functions | log 100 = 2 |
| √ | Square root | √16 = 4 |
| x² | Square | 4² = 16 |
| xʸ | Power | 2³ = 8 |
| π | Pi constant | ≈ 3.14159 |
| e | Euler's number | ≈ 2.71828 |

#### Unit Converter Usage

1. Select a category (Length, Area, Weight, Temperature, Currency)
2. Choose source unit from "从" dropdown
3. Enter value in the input field
4. Select target unit from "转换为" dropdown
5. Result displays automatically

### Build

```bash
cd calculator-android
./gradlew assembleDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### Tech Stack

- Kotlin
- Android SDK
- Material Design
- ViewPager2 + TabLayout

---

## 中文

### 简介

一款 Android 计算器应用，包含两个主要功能：
1. **科学计算器** - 高级数学运算
2. **换算计算器** - 常用单位换算

### 功能特点

#### 科学计算器
- **基本运算**: 加法(+)、减法(-)、乘法(×)、除法(÷)
- **百分比**: 百分比计算 (%)
- **幂运算**: 乘方(xʸ)、平方(x²)
- **三角函数**: sin、cos、tan（角度制）
- **对数函数**: log（常用对数）、ln（自然对数）
- **平方根**: √
- **数学常数**: π（圆周率）、e（自然常数）

#### 换算计算器
- **长度**: 毫米、厘米、米、千米、英寸、英尺、码、英里
- **面积**: 平方毫米、平方厘米、平方米、平方千米、平方英寸、平方英尺、平方码、英亩、公顷
- **重量**: 毫克、克、千克、吨、盎司、磅、英石
- **温度**: 摄氏度(°C)、华氏度(°F)、开尔文(K)
- **汇率**: 人民币、美元、欧元、日元、英镑、港币、韩元

### 使用方法

1. 下载并安装 APK 到 Android 设备
2. 点击标签页切换功能：
   - **科学计算器**
   - **换算计算器**

#### 科学计算器按键说明

| 按键 | 功能 | 示例 |
|------|------|------|
| AC | 全部清除 | 重置计算器 |
| ⌫ | 删除最后一位 | 删除最后输入 |
| sin/cos/tan | 三角函数 | sin 30 = 0.5 |
| log/ln | 对数函数 | log 100 = 2 |
| √ | 平方根 | √16 = 4 |
| x² | 平方 | 4² = 16 |
| xʸ | 幂运算 | 2³ = 8 |
| π | 圆周率常数 | ≈ 3.14159 |
| e | 自然常数 | ≈ 2.71828 |

#### 换算计算器使用方法

1. 选择类别（长度、面积、重量、温度、汇率）
2. 从"从"下拉菜单选择源单位
3. 在输入框中输入数值
4. 从"转换为"下拉菜单选择目标单位
5. 结果自动显示

### 编译

```bash
cd calculator-android
./gradlew assembleDebug
```

APK 文件位置: `app/build/outputs/apk/debug/app-debug.apk`

### 技术栈

- Kotlin
- Android SDK
- Material Design
- ViewPager2 + TabLayout

---

## Screenshots / 截图

Coming soon...

## License / 许可证

MIT License
