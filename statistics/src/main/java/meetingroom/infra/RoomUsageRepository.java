package meetingroom.infra;

import java.util.List;
import meetingroom.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "roomUsages",
    path = "roomUsages"
)
public interface RoomUsageRepository
    extends PagingAndSortingRepository<RoomUsage, Long> {}
