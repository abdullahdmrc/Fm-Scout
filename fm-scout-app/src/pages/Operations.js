import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Operations.css";

const Operations = () => {
  const [entityType, setEntityType] = useState("");
  const [operation, setOperation] = useState("");
  const [attributes, setAttributes] = useState({});
  const [dataList, setDataList] = useState([]);
  const [message, setMessage] = useState("");
  const [editMode, setEditMode] = useState(false);

  const entityOptions = ["clubs", "football-players", "users"];
  const operationOptions = entityType === "users" ? ["delete"] : ["insert", "delete", "update"];

  useEffect(() => {
    setOperation("");
    setAttributes({});
    setDataList([]);
    setMessage("");
    setEditMode(false);
  }, [entityType]);

  useEffect(() => {
    if (operation === "insert" || operation === "update") {
      setAttributes({});
      setEditMode(false);
    }
  }, [operation]);

  const getIdField = (entityType) => {
    if (entityType === "clubs") return "clubId";
    if (entityType === "football-players") return "playerId";
    if (entityType === "users") return "userId";
    return "id";
  };


  const fetchData = async () => {
    try {
      let response = null;

      if (entityType === "users") {
        response = await axios.get("http://localhost:8080/api/v1/users/non-admins");
      } else if (entityType === "clubs") {
        response = await axios.get("http://localhost:8080/api/v1/clubs");
      } else if (entityType === "football-players") {
        response = await axios.get("http://localhost:8080/api/v1/football-players");
      }

      if (response) {
        setDataList(response.data);
        setMessage("");
      } else {
        setMessage("Invalid entity type selected.");
      }
    } catch (error) {
      setMessage("An error occurred while fetching data.");
      console.error(error);
    }
  };

  const sendData = async (method, url, payload) => {
    try {
      if (method === "delete") {
        await axios.delete(url);
      } else {
        // Payload içinden clubId yi sil
        if (payload.id) {
          payload.id = Number(payload.id); // Sayıya dönüştür
        }
        if (payload.leagueId) {
          payload.leagueId = Number(payload.leagueId); // Sayıya dönüştür
        }
        if (payload.managerId) {
          payload.managerId = Number(payload.managerId); // Sayıya dönüştür
        }
        if (payload.reputation) {
          payload.reputation = Number(payload.reputation); // Sayıya dönüştür
        }

        if (payload.foundationDate) {
          payload.foundationDate = Number(payload.foundationDate);
        }






        await axios({
          method,
          url,
          data: payload,
        });
      }
      setMessage("Successfully done!");
      fetchData();
      setAttributes({});
      setEditMode(false);
    } catch (error) {
      setMessage("An error occurred during the operation.");
      console.error(error);
    }
  };

  const handleEditClick = (item) => {
    setAttributes(item);
    setEditMode(true);
  };

  const handleInputChange = (key, value) => {
    if (key === "positions") {
      const positionsArray = value.split(",").map((pos) => pos.trim()); // Virgülle ayır ve listeye çevir
      setAttributes({ ...attributes, [key]: positionsArray });
    } else {
      setAttributes({ ...attributes, [key]: value });
    }
  };


  const renderForm = () => {
    if (operation === "insert" && !editMode) {
      const initialAttributes = {};
      if (entityType === "clubs") {
        initialAttributes.clubName = '';
        initialAttributes.foundationDate = '';
        initialAttributes.leagueName = '';
        initialAttributes.reputation = '';
        initialAttributes.presidentName = '';
        initialAttributes.managerName = '';
      } else if (entityType === "football-players") {
        initialAttributes.name = '';
        initialAttributes.age = '';
        initialAttributes.countryName = '';
        initialAttributes.clubName = '';
        initialAttributes.positions = '';
        initialAttributes.ca = '';
        initialAttributes.pa = '';
        initialAttributes.corners = '';
        initialAttributes.crossing = '';
        initialAttributes.dribbling = '';
        initialAttributes.finishing = '';
        initialAttributes.firstTouch = '';
        initialAttributes.freeKickTaking = '';
        initialAttributes.heading = '';
        initialAttributes.longShots = '';
        initialAttributes.longThrows = '';
        initialAttributes.marking = '';
        initialAttributes.passing = '';
        initialAttributes.penaltyTaking = '';
        initialAttributes.tackling = '';
        initialAttributes.technique = '';
        initialAttributes.aggression = '';
        initialAttributes.anticipation = '';
        initialAttributes.bravery = '';
        initialAttributes.composure = '';
        initialAttributes.concentration = '';
        initialAttributes.vision = '';
        initialAttributes.decision = '';
        initialAttributes.determination = '';
        initialAttributes.flair = '';
        initialAttributes.leadership = '';
        initialAttributes.offTheBall = '';
        initialAttributes.positioning = '';
        initialAttributes.teamWork = '';
        initialAttributes.workRate = '';
        initialAttributes.acceleration = '';
        initialAttributes.agility = '';
        initialAttributes.balance = '';
        initialAttributes.jumpingReach = '';
        initialAttributes.naturalFitness = '';
        initialAttributes.pace = '';
        initialAttributes.stamina = '';
        initialAttributes.strength = '';
        initialAttributes.stability = '';
        initialAttributes.height = '';
        initialAttributes.leftFoot = '';
        initialAttributes.rightFoot = '';
        initialAttributes.marketValue = '';
        initialAttributes.salary = '';
        initialAttributes.rentalClubName = '';
      }
      return (
        <div>
          <h3>Insert {entityType}</h3>
          {Object.keys(initialAttributes).map((key) => {
            let inputType = "text"; // Varsayılan olarak text

            // Türlere göre input tipini ayarla
            if (["reputation", "foundationDate", "ca", "pa"].includes(key)) inputType = "number";

            return (
              <div key={key}>
                <label>{key}: </label>
                <input
                  type={inputType}
                  value={attributes[key] || ""}
                  onChange={(e) => {
                    let value = e.target.value;
                    // `positions` alanını diziye çevirmek için özel kontrol
                    if (key === "positions") {
                      value = value.split(",").map((pos) => pos.trim());

                    }


                    setAttributes({ ...attributes, [key]: value });
                  }}
                />
              </div>
            );
          })}
          <button onClick={() => sendData("POST", `http://localhost:8080/api/v1/${entityType}`, attributes)}>
            Submit
          </button>
        </div>
      );
    } else if (operation === "delete") {
      return (
        <div>
          <h3>Delete {entityType}</h3>
          <button onClick={fetchData}>Fetch All</button>
          <ul>
            {dataList.map((item, index) => {
              const idField = getIdField(entityType);
              const itemId = item[idField];
              return (
                <li key={index}>
                  {`ID: ${itemId}, Name: ${item.userName || item.name || item.clubName}`}{" "}
                  <button
                    onClick={() => {
                      if (!itemId) {
                        alert(`Invalid ID for item: ${JSON.stringify(item)}`);
                        return;
                      }
                      sendData("delete", `http://localhost:8080/api/v1/${entityType}/${itemId}`);
                    }}
                  >
                    Delete
                  </button>
                </li>
              );
            })}
          </ul>
        </div>
      );
    } else if (operation === "update") {
      return (
        <div>
          <h3>Update {entityType}</h3>
          <button onClick={fetchData}>Fetch All</button>
          <ul>
            {dataList.map((item, index) => (
              <li key={index}>
                {`ID: ${item[getIdField(entityType)]}, Name: ${item.userName || item.name || item.clubName}`}
                <button onClick={() => handleEditClick(item)}>Edit</button>
              </li>
            ))}
          </ul>
          {editMode && Object.keys(attributes).length > 0 && (
            <div>
              <h4>Edit Attributes</h4>
              {Object.keys(attributes)
                .filter((key) => {
                  // entityType'a bağlı olarak filtreleme
                  if (entityType === "football-players")
                    return !["playerId", "clubId", "addToFavourites"].includes(key); // İstenmeyen alanları filtrele
                  if (entityType === "clubs")
                    return key !== "clubId"; // Kulüp güncelleme, clubId hariç
                  return true; // Diğer türler için hiçbir filtreleme yok
                })
                .map((key) => (
                  <div key={key}>
                    <label>{key}: </label>
                    <input
                      type="text"
                      value={attributes[key] || ""}
                      onChange={(e) => handleInputChange(key, e.target.value)} />
                  </div>
                ))}
              <button
                onClick={() =>
                  sendData(
                    "PUT",
                    `http://localhost:8080/api/v1/${entityType}/${attributes[getIdField(entityType)]}`,
                    attributes
                  )
                }
              >
                Update
              </button>
            </div>
          )}
        </div>
      );
    }

    return null;

  };

  return (
    /*<div className="operations">
      <h2 className="operations__header">Admin Operations</h2>
      <div>
        <label className="operations__label">Select Entity: </label>
        <select className="operations__select" value={entityType} onChange={(e) => setEntityType(e.target.value)}>
          <option value="" disabled>
            -- Select --
          </option>
          {entityOptions.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      </div>

      {entityType && (
        <div>
          <label>Select Operation: </label>
          <select value={operation} onChange={(e) => setOperation(e.target.value)}>
            <option value="" disabled>
              -- Select --
            </option>
            {operationOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>
      )}

      {operation && renderForm()}
      {message && <div className="message">{message}</div>}
    </div>*/
    <div class="operations">
      <h2 class="operations__header">Admin Operations</h2>
      <div>
        <label class="operations__label">Select Entity: </label>
        <select class="operations__select" value={entityType}
          onChange={(e) => setEntityType(e.target.value)}>
          <option value="" disabled>
            -- Select --
          </option>
          {entityOptions.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      </div>

      {entityType && (
        <div>
          <label>Select Operation: </label>
          <select value={operation} onChange={(e) => setOperation(e.target.value)}>
            <option value="" disabled>
              -- Select --
            </option>
            {operationOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>
      )}

      {operation === "update" && (
        <div>
          <h3>Update {entityType}</h3>
          <button onClick={fetchData}>Fetch All</button>
          <ul>
            {dataList.map((item, index) => (
              <li key={index}>
                {`ID: ${item[getIdField(entityType)]}, Name: ${item.userName || item.name || item.clubName}`}
                <button onClick={() => handleEditClick(item)}>Edit</button>
                {editMode && attributes[getIdField(entityType)] === item[getIdField(entityType)] && Object.keys(attributes).length > 0 && (
                  <div>
                    <h4>Edit Attributes</h4>
                    {Object.keys(attributes)
                      .filter((key) => {
                        // entityType'a bağlı olarak filtreleme
                        if (entityType === "football-players")
                          return !["playerId", "clubId", "addToFavourites"].includes(key); // İstenmeyen alanları filtrele
                        if (entityType === "clubs")
                          return key !== "clubId"; // Kulüp güncelleme, clubId hariç
                        return true; // Diğer türler için hiçbir filtreleme yok
                      })
                      .map((key) => (
                        <div key={key}>
                          <label>{key}: </label>
                          <input
                            type="text"
                            value={attributes[key] || ""}
                            onChange={(e) => handleInputChange(key, e.target.value)} />
                        </div>
                      ))}
                    <button
                      onClick={() =>
                        sendData(
                          "PUT",
                          `http://localhost:8080/api/v1/${entityType}/${attributes[getIdField(entityType)]}`,
                          attributes
                        )
                      }
                    >
                      Update
                    </button>
                  </div>
                )}
              </li>
            ))}
          </ul>
        </div>
      )}

      {operation === "insert" && !editMode && renderForm()}
      {operation === "delete" && renderForm()}
      {message && <div class="message">{message}</div>}
    </div>

  );
};

export default Operations;


