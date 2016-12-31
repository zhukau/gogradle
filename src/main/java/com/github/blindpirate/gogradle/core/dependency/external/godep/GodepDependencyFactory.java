package com.github.blindpirate.gogradle.core.dependency.external.godep;

import com.github.blindpirate.gogradle.core.dependency.resolve.ExternalDependencyFactory;

import javax.inject.Singleton;
import java.io.File;
import java.util.List;
import java.util.Map;

import static com.github.blindpirate.gogradle.util.DataExchange.parseJson;

/**
 * Converts Godeps/Godeps.json in repos managed by godep to gogradle map notations.
 *
 * @see <a href="https://github.com/tools/godep">godep</a> for more details.
 */
@Singleton
public class GodepDependencyFactory extends ExternalDependencyFactory {

    protected String identityFileName() {
        return "Godeps/Godeps.json";
    }

    protected List<Map<String, Object>> adapt(File file) {
        GodepsDotJsonModel model = parseJson(file, GodepsDotJsonModel.class);
        return model.toNotations();
    }
}
