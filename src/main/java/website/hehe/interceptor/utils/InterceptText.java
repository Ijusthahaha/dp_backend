package website.hehe.interceptor.utils;

public class InterceptText {
    public static final String[] TEXT = {
            "这辈子最擅长的事，就是在热闹中表演孤独，在孤独时假装热闹。",
            "我只是习惯了你，并非缺你不可",
            "我们总是像智者一样去劝慰别人，却像傻子一样折磨自己，不要埋怨别人让你失望，很多时候，跟自己过不去的是自己。",
            "我们寻找的就是不在孤独，是一个人或者一个地方，生命中幸福的所在，只是我们还没有找到。",
            "若不抽出时间来创造自己想要的生活，你最终将不得不花费大量的时间来应付自己不想要的生活。",
            "在成长的路上，懂得了，有一些习惯我们必须戒掉；在成长的路上，我们也曾经拒绝长大，但是后来慢慢明白了，没有人可以拒绝去成长。于是，我们学会了努力，为我们的未来努力。",
            "人之所迷，因在局内，人之所悟，因在局外。人生充满变数，定力如何，直接影响到人生的走向。淡定看人生，宁静看自己。",
            "人的一生，都有一些说不出的秘密，挽不回的遗憾，触不到的梦想，忘不了的爱。",
            "人总是会本能地去拒绝生活中的消极一面，其中也就是包括消极的情绪、消极的想法等等。拒绝的方式多种多样，或者是努力告诉自己不去想它，或者是极力地否认它的存在。",
            "越长大，越知道，做事不容易，越知道，每个人都有难处，也就越不再随随便便发表评论，或者瞧不起谁。这不是虚伪，而是懂得体谅，温柔地和这个世界相处。",
            "如果分手的恋人还能做朋友，要不从没爱过，要不还在爱着。",
            "我宁愿与他是平行线，永远不会相交，因为一旦相交，过了那个点就会越离越远。"
    };
    public static String getTEXT() {
        int index = (int) (Math.random() * TEXT.length);
        return TEXT[index];
    }
}
