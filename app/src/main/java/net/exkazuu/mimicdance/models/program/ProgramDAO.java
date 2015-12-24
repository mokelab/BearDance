package net.exkazuu.mimicdance.models.program;

import java.util.List;

/**
 * {@link Program}を保存したりするためのinterface
 */
public interface ProgramDAO {
    /**
     * 保存します
     * @param programList プログラムのリスト
     */
    void save(List<Program> programList);

    /**
     * 読み込みます
     * @return プログラムのリスト。保存されていない場合は長さ12のリスト
     */
    List<Program> load();
}
