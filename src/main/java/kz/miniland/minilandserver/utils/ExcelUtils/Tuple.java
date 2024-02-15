package kz.miniland.minilandserver.utils.ExcelUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Tuple<K, V> {
    private K key;
    private V value;
}