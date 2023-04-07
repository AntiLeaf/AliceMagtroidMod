# 杀戮尖塔 爱丽丝 Mod

本仓库是一个 [**杀戮尖塔**](https://store.steampowered.com/app/646570/Slay_the_Spire/) 模组，
添加了一名新的可选角色 [**爱丽丝·玛格特罗伊德**](https://zh.moegirl.org.cn/%E7%88%B1%E4%B8%BD%E4%B8%9D%C2%B7%E7%8E%9B%E6%A0%BC%E7%89%B9%E7%BD%97%E4%BE%9D%E5%BE%B7/)。

爱丽丝是居住在魔法之森的魔法使，拥有操控人偶程度的能力。

## 依赖模组
* [ModTheSpire](https://github.com/kiooeht/ModTheSpire) ：杀戮尖塔 Mod 加载器
* [BaseMod](https://github.com/daviscook477/BaseMod) ：一个 API Mod，同时也提供了游戏内调试用命令行
* [StSLib](https://github.com/kiooeht/StSLib) ：一个供其他 Mod 使用的关键词和机制的集合

## 新机制

### 人偶

爱丽丝可以操控 **人偶**，**人偶** 们会帮助爱丽丝进行战斗，提供各种功能。**人偶** 们拥有生命值。
- **人偶队列**：**人偶** 们会排成一排战斗，一排最多存在 5 个 **人偶**。
  - 调整队列可以让 **人偶** 们并排战斗，最多 5 排。
- **最右侧**：最右侧的 **人偶** 会替爱丽丝承受伤害，每个敌人的伤害将会按顺序由每行最右侧的 **人偶** 承受。
  - **人偶** 被攻击破坏时，溢出的伤害将由爱丽丝承担。
  - **人偶** 们被指挥前往最右侧时会优先选择一排空的队列。
- **生成**：**生成** 新的 **人偶** 时，可以任选一个 **人偶队列**，新的人偶将会 **生成** 在其最右侧。
  - 部分卡牌或 **伦敦人偶** 会有不同的生成位置。
  - 如果选择的队列已满，则会先将最左侧的 **人偶** **回收**。
- **回收**：在已满的队列 **生成** **人偶**时，最左侧的 **人偶** 会消失并触发 **回收** 效果。
  - 部分卡牌也可以主动 **回收** **人偶**。
- **指挥**：部分卡牌可以选择指向某个 **人偶** 打出。若如此做，则会执行对应的效果，并触发一次 **人偶** 的 **行动** 效果。
- **行动**：**人偶** 们被爱丽丝 **指挥** 时产生的效果。
  - 以下的介绍中，如果有括号内注明的时机，则表示它也会在对应时机自动 **行动** 一次。
  - 作为特例，**伦敦人偶** 在自动 **行动** 时和被 **指挥** 时的效果是不同的。

爱丽丝总共有 7 种 **人偶**，每种人偶都有不同的效果。括号内的数值表示人偶的生命值。

（七色的人偶使有七种人偶很合理吧！）

- **上海** (4)：**上海人偶** 在生成时立刻对随机敌人造成 8 伤害。
  - **行动** (回合结束时)：对所有敌人造成 2 伤害。
  - **蓬莱**：生成时额外造成 4 伤害，**行动** 时额外造成 2 伤害。
- **蓬莱** (5)：每个 **蓬莱人偶** 存在时，将使其他类型 **人偶** 拥有更强的能力。
  - **行动** (被 **指挥** 时)：使最右侧的所有 **人偶** **行动** 一次。
- **法兰西** (8)：在生成时带有 3 格挡。**法兰西人偶** 被破坏时可以吸收溢出的伤害。
  - **行动** (回合开始时)：获得 3 格挡。
  - **蓬莱**：生成与行动时额外获得 3 格挡。
- **奥尔良** (5)：**奥尔良人偶** 在生成时立刻为爱丽丝提供 3 格挡。
  - **行动** (回合开始时)：为所有 **人偶** 与爱丽丝提供 1 格挡。
  - **蓬莱**：生成时额外提供 2 格挡。**行动** 时额外提供 1 格挡。
- **荷兰** (4)：每个 **荷兰人偶** 为爱丽丝提供 1 点 **力量** 与 **敏捷**。
  - **行动** (回合开始时)：爱丽丝获得 4 **活力**。
  - **蓬莱**：每次行动额外提供 2 **活力**。
- **伦敦** (1)：**伦敦人偶** 在 **生成** 时默认在队列最左侧。
  - 被破坏后，会在队列最右侧随机再 **生成** 一个其他类型 **人偶**。
  - **行动** (回合开始或结束时)：对最左侧敌人造成 3 点伤害。
  - **行动** (被 **指挥** 或被 **蓬莱人偶** 触发时)：回到队列最左侧，并给予爱丽丝 3 点格挡。
  - **蓬莱**：造成的伤害和提供的格挡额外提高 2 点。
- **京都** (3)：每个 **京都人偶** 可以使爱丽丝和所有人偶在回合开始时保留最多 6 点格挡。
  - **行动**：抽一张牌。
  - **蓬莱**：每有一个 **蓬莱人偶**，**京都人偶** 就在 **行动** 时对随机敌人造成等同于爱丽丝格挡的 25% 的伤害。

### 其他关键词

- **活力**：使下一张攻击牌的伤害提高。
- **补充**：抽到这张牌时，再抽一张牌。
- **暂时性生命**：一种不会在回合开始时减少的格挡。详见 [StSLib](https://github.com/kiooeht/StSLib)。
- **脱离**：当这张牌未被打出而离开手牌时，触发某些效果。

## 致谢
  - Thanks to the [Marisa Mod](https://github.com/lf201014/STS_ThMod_MRS), which teaches me how to develop a mod of STS.
