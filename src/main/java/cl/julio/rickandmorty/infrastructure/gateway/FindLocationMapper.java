package cl.julio.rickandmorty.infrastructure.gateway;

import cl.julio.rickandmorty.domain.model.Origin;
import cl.julio.rickandmorty.infrastructure.gateway.model.ApiLocation;
import cl.julio.rickandmorty.util.State;

public final class FindLocationMapper {

    private FindLocationMapper() {
        // Non-instantiable class
    }

    public static State<Origin> mapApiResponse(State<ApiLocation> apiLocationState) {
        State<Origin> originState = new State<>();
        if (apiLocationState.withErrors()) {
            originState.setErrorMessage(apiLocationState.getErrorMessage());
            originState.setErrorDetail(apiLocationState.getErrorDetail());
            return originState;
        }

        Origin Origin = mapFrom(apiLocationState.getData());
        originState.setData(Origin);

        return originState;
    }

    public static Origin mapFrom(ApiLocation apiLocation) {
        Origin origin = new Origin();
        origin.setName(apiLocation.getName());
        origin.setUrl(apiLocation.getUrl());
        origin.setDimension(apiLocation.getDimension());
        origin.setResidents(apiLocation.getResidents());

        return origin;
    }

}
