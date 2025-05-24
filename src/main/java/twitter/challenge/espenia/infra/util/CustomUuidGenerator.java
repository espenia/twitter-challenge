package twitter.challenge.espenia.infra.util;

import com.fasterxml.uuid.Generators;
import java.io.Serial;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomUuidGenerator implements IdentifierGenerator {

  @Serial private static final long serialVersionUID = 2711845884814743109L;

  @Override
  public Object generate(SharedSessionContractImplementor session, Object object) {
    return Generators.timeBasedEpochGenerator().generate();
  }
}
