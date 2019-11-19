class ColorContainer {
  List<int> colors = [];

  void addColor(int color) {
    colors.add(color);
  }
  
  String json() {
    return '{"colors":${colors.toString()}}';
  }
}